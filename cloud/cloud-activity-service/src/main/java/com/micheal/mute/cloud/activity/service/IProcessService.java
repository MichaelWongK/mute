package com.micheal.mute.cloud.activity.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.micheal.mute.cloud.activity.domain.HistoricActivity;
import com.micheal.mute.commons.page.PageDomain;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:wangmk13@163.com">micheal.wang</a>
 * @date 2021/5/8 17:32
 * @Description
 */
public interface IProcessService {

    /**
     * 查询审批历史列表
     * @param processInstanceId
     * @param historicActivity
     * @return
     */
    Page<HistoricActivity> selectHistoryList(String processInstanceId, HistoricActivity historicActivity, PageDomain pageDomain);

    /**
     * 提交申请
     * @param applyUserId 申请人
     * @param businessKey 业务表 id
     * @param itemName
     * @param itemContent
     * @param key 流程定义 key
     * @param variables 流程变量
     * @return
     */
    ProcessInstance submitApply(String applyUserId, String businessKey, String itemName, String itemContent, String key, Map<String, Object> variables);

    /**
     * 查询待办任务列表
     * @param userId
     * @param key
     * @return
     */
    Page<Task> findTodoTasks(String userId, String key, PageDomain pageDomain);

    /**
     * 查询已办任务列表
     * @param userId
     * @param key
     * @return
     */
    List<HistoricTaskInstance>findDoneTasks(String userId, String key);

    /**
     * 办结任务
     * @param taskId        任务id
     * @param instanceId    流程实例id
     * @param itemName      标题
     * @param itemContent   内容
     * @param module        模块 processDefinitionKey
     * @param variables     流程参数
     * @param request       httpRequest
     */
    void complete(String username, String taskId, String instanceId, String itemName, String itemContent, String module, Map<String, Object> variables, HttpServletRequest request);

    /**
     * 委托任务
     * @param taskId
     * @param fromUser
     * @param delegateToUser
     */
    void delegate(String taskId, String fromUser, String delegateToUser);

    void cancelApply(String instanceId, String deleteReason);

    void suspendOrActiveApply(String instanceId, String suspendState);

    String findBusinessKeyByInstanceId(String instanceId);
}
