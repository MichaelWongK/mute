package com.micheal.mute.cloud.activity.service.Impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.micheal.mute.cloud.activity.domain.BizTodoItem;
import com.micheal.mute.cloud.activity.domain.HistoricActivity;
import com.micheal.mute.cloud.activity.service.IBizTodoItemService;
import com.micheal.mute.cloud.activity.service.IProcessService;
import com.micheal.mute.commons.page.PageDomain;
import com.micheal.mute.commons.utils.StringUtils;
import com.micheal.mute.provider.api.UmsAdminService;
import com.micheal.mute.provider.domain.UmsAdmin;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:wangmk13@163.com">micheal.wang</a>
 * @date 2021/5/8 17:33
 * @Description 流程实例相关操作
 */
@Slf4j
@Service
@Transactional
public class ProcessServiceImpl implements IProcessService {

    @Autowired
    private IdentityService identityService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private IBizTodoItemService bizTodoItemService;
    @Reference(version = "1.0.0")
    private UmsAdminService umsAdminService;

    @Override
    public Page<HistoricActivity> selectHistoryList(String processInstanceId, HistoricActivity historicActivity, PageDomain pageDomain) {
        Page<HistoricActivity> page = new Page<>();
        List<HistoricActivity> activityList = new ArrayList<>();
        HistoricActivityInstanceQuery historyQuery = historyService.createHistoricActivityInstanceQuery();
        if (StringUtils.isNotBlank(historicActivity.getAssignee())) {
            historyQuery.taskAssignee(historicActivity.getAssignee());
        }
        if (StringUtils.isNotBlank(historicActivity.getActivityName())) {
            historyQuery.activityName(historicActivity.getActivityName());
        }
        List<HistoricActivityInstance> list = historyQuery.processInstanceId(processInstanceId)
                .activityType("userTask")
                .orderByHistoricActivityInstanceStartTime()
                .listPage((pageDomain.getPageNum() - 1) * pageDomain.getPageSize(), pageDomain.getPageSize());
        list.forEach(instance -> {
            HistoricActivity activity = new HistoricActivity();
            BeanUtils.copyProperties(instance, activity);
            String taskId = instance.getTaskId();
            List<Comment> taskComments = taskService.getTaskComments(taskId);
            if (!CollectionUtils.isEmpty(taskComments)) {
                activity.setComment(taskComments.get(0).getFullMessage());
            }
            UmsAdmin umsAdmin = umsAdminService.get(instance.getAssignee());
            if (umsAdmin != null) {
                activity.setAssigneeName(umsAdmin.getNickName());
            }
            activityList.add(activity);
        });
        page.setRecords(activityList);
        page.setCurrent(pageDomain.getPageNum());
        page.setSize(pageDomain.getPageSize());
        page.setTotal(historyQuery.count());
        return page;
    }

    @Override
    public ProcessInstance submitApply(String applyUserId, String businessKey, String itemName, String itemContent, String module, Map<String, Object> variables) {
        // 用来设置启动流程的人员ID，引擎自动把用户ID保存至activity:initiator
        identityService.setAuthenticatedUserId(applyUserId);
        // 启动流程时设置业务 key
        ProcessInstance instance = runtimeService.startProcessInstanceByKey(module, businessKey, variables);
        // 下一节点处理人待办事项
        bizTodoItemService.insertTodoItem(instance.getProcessInstanceId(), itemName, itemContent, module);
        return instance;
    }

    @Override
    public Page<Task> findTodoTasks(String username, String processDefinitionKey, PageDomain pageDomain) {
        Page<Task> page = new Page<>();
        TaskQuery taskQuery = taskService.createTaskQuery();
        if (StringUtils.isNotBlank(processDefinitionKey)) {
            taskQuery.processDefinitionKey(processDefinitionKey);
        }
        if (StringUtils.isNotBlank(username)) {
            // assignee 代理人查询
            taskQuery.taskCandidateOrAssigned(username);
            List<String> groups = umsAdminService.getGroupsByUsername(username);
            if (!CollectionUtils.isEmpty(groups)) {
                // 通过username -> user groupIds
                taskQuery.taskCandidateGroupIn(groups);
            }
        }
        // 分页
        List<Task> tasks = taskQuery.listPage((pageDomain.getPageNum() - 1) * pageDomain.getPageSize(), pageDomain.getPageSize());
        page.setRecords(tasks)
                .setCurrent(pageDomain.getPageNum())
                .setSize(pageDomain.getPageSize())
                .setTotal(taskQuery.count());
        return page;
    }

    @Override
    public List<HistoricTaskInstance> findDoneTasks(String userId, String processDefinitionKey) {
        List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery()
                .processDefinitionKey(processDefinitionKey)
                .taskAssignee(userId)
                .finished()
                .orderByHistoricTaskInstanceEndTime()
                .desc()
                .list();

        return list;
    }

    @Override
    public void complete(String username, String taskId, String instanceId, String itemName, String itemContent, String module, Map<String, Object> variables, HttpServletRequest request) {
        Enumeration<String> parameterNames = request.getParameterNames();
        String comment = null;  // 审批批注
        boolean agree = true;
        try {
            while (parameterNames.hasMoreElements()) {
                String parameterName = parameterNames.nextElement();
                if (parameterName.startsWith("p_")) {
                    // 参数结构：p_B_name，p为参数的前缀，B为类型，name为属性名称
                    String[] parameter = parameterName.split("_");
                    if (parameter.length == 3) {
                        String paramValue = request.getParameter(parameterName);
                        Object value = paramValue;
                        if (parameter[1].equals("B")) {
                            value = BooleanUtils.toBoolean(paramValue);
                            agree = (boolean) value;
                        } else if (parameter[1].equals("DT")) {
                            value = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(paramValue);
                        } else if (parameter[1].equals("COM")) {
                            comment = paramValue;
                        }
                        variables.put(parameter[2], value);
                    } else {
                        throw new RuntimeException("invalid parameter for activiti variable: " + parameterName);
                    }
                }
            }
            if (StringUtils.isNotBlank(comment)) {
                identityService.setAuthenticatedUserId(username);
                comment = agree ? "【同意】" + comment : "【拒绝】" + comment;
                taskService.addComment(taskId, instanceId, comment);
            }

            // 被委派人处理完任务
            // 被委托流程需要先 resolved 这个任务再提交
            // 所以 complete 前先提交
            // resolveTask() 要在 claim() 之前，不然 act_hi_taskinst 表的 assignee 字段会为 null
            taskService.resolveTask(taskId, variables);
            // 只有签收任务，act_hi_taskinst 表的 assignee 字段才不为 null
            taskService.claim(taskId, username);
            taskService.complete(taskId, variables);

            // 更新待办事项中已办人状态
            BizTodoItem updateItem = bizTodoItemService.selectBizTodoItemByCondition(taskId, username);
            updateItem.setIsView("1");
            updateItem.setIsHandle("1");
            updateItem.setHandleUserId(username);
            updateItem.setHandleUserName(updateItem.getTodoUserName());
            updateItem.setHandleTime(new Date());
            bizTodoItemService.updateBizTodoItem(updateItem);

            // 处理群租任务待办事项状态
            BizTodoItem queryItem = new BizTodoItem();
            queryItem.setTaskId(taskId);
            queryItem.setIsHandle("0");
            List<BizTodoItem> bizTodoItems = bizTodoItemService.selectBizTodoItemList(queryItem);
            List<Long> ids = bizTodoItems.stream().filter(bizTodoItem -> bizTodoItem.getTodoUserId().equals(username)).map(BizTodoItem::getId).collect(Collectors.toList());
            // 删除候选用户组其他 todoitem
            bizTodoItemService.deleteBizTodoItemByIds((String[]) ids.toArray());

            // 下一节点处理人待办事项处理
            bizTodoItemService.insertTodoItem(instanceId, itemName, itemContent, module);
        } catch (Exception e) {
            log.error("error on complete task {}, variables={}", new Object[]{taskId, variables, e});
        }

    }

    @Override
    public void delegate(String taskId, String fromUser, String delegateToUser) {
        taskService.delegateTask(taskId, delegateToUser);
        // 更新待办事项
        BizTodoItem updateItem = bizTodoItemService.selectBizTodoItemByCondition(taskId, fromUser);
        if (updateItem != null) {
            UmsAdmin umsAdmin = umsAdminService.get(delegateToUser);
            updateItem.setTodoUserId(delegateToUser);
            updateItem.setTodoUserName(umsAdmin.getNickName());
            bizTodoItemService.updateBizTodoItem(updateItem);
        }

    }

    @Override
    public void cancelApply(String instanceId, String deleteReason) {
        runtimeService.deleteProcessInstance(instanceId, deleteReason);
    }

    @Override
    public void suspendOrActiveApply(String instanceId, String suspendState) {
        if ("1".equals(suspendState)) {
            // 当流程实例被挂起时，无法通过下一个节点对应的任务id来继续这个流程实例。
            // 通过挂起某一特定的流程实例，可以终止当前的流程实例，而不影响到该流程定义的其他流程实例。
            // 激活之后可以继续该流程实例，不会对后续任务造成影响。
            // 直观变化：act_ru_task 的 SUSPENSION_STATE_ 为 2
            runtimeService.suspendProcessInstanceById(instanceId);
        } else if ("2".equals(suspendState)) {
            runtimeService.activateProcessInstanceById(instanceId);
        }

    }

    @Override
    public String findBusinessKeyByInstanceId(String instanceId) {
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(instanceId).singleResult();
        if (processInstance == null) {
            HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(instanceId).singleResult();
            return historicProcessInstance.getBusinessKey();
        }
        return processInstance.getBusinessKey();
    }
}
