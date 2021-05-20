package com.micheal.mute.cloud.activity.service.Impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.micheal.mute.cloud.activity.domain.BizTodoItem;
import com.micheal.mute.cloud.activity.mapper.BizTodoItemMapper;
import com.micheal.mute.cloud.activity.service.IBizTodoItemService;
import com.micheal.mute.commons.page.PageDomain;
import com.micheal.mute.commons.utils.StringUtils;
import com.micheal.mute.provider.api.UmsAdminService;
import com.micheal.mute.provider.domain.UmsAdmin;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * @author <a href="mailto:wangmk13@163.com">micheal.wang</a>
 * @date 2021/5/17 15:13
 * @Description 待办事项Service业务层处理
 */
@Service
public class BizTodoItemServiceImpl implements IBizTodoItemService {

    @Autowired
    private TaskService taskService;
    @Autowired
    private IBizTodoItemService todoItemService;
    @Autowired
    private BizTodoItemMapper bizTodoItemMapper;
    @Reference(version = "1.0.0")
    private UmsAdminService umsAdminService;

    @Override
    public BizTodoItem selectBizTodoItemById(Long id) {
        return null;
    }

    @Override
    public IPage<BizTodoItem> selectBizTodoItemPage(BizTodoItem bizTodoItem, PageDomain pageDomain) {
        Page<BizTodoItem> page = new Page(pageDomain.getPageNum(),pageDomain.getPageSize(), true);
        IPage<BizTodoItem> bizTodoItemPage = bizTodoItemMapper.selectBizTodoItemPage(page, bizTodoItem);
        return bizTodoItemPage;
    }

    @Override
    public List<BizTodoItem> selectBizTodoItemList(BizTodoItem bizTodoItem) {

        return bizTodoItemMapper.selectBizTodoItemList(bizTodoItem);
    }

    @Override
    public int insertBizTodoItem(BizTodoItem bizTodoItem) {
        return 0;
    }

    @Override
    public int updateBizTodoItem(BizTodoItem bizTodoItem) {
        return 0;
    }

    @Override
    public int deleteBizTodoItemByIds(String[] ids) {
        return bizTodoItemMapper.deleteBizTodoItemByIds(ids);
    }

    @Override
    public int deleteBizTodoItemById(String id) {
        return 0;
    }

    @Override
    public int insertTodoItem(String instanceId, String itemName, String itemContent, String module) {
        BizTodoItem bizTodoItem = new BizTodoItem();
        bizTodoItem.setInstanceId(instanceId);
        bizTodoItem.setItemName(itemName);
        bizTodoItem.setItemContent(itemContent);
        bizTodoItem.setModule(module);
        bizTodoItem.setIsHandle("0");
        bizTodoItem.setIsView("0");
        bizTodoItem.setTodoTime(new Date());

        List<Task> taskList = taskService.createTaskQuery().processInstanceId(instanceId).active().list();
        int count = 0;
        for (Task task : taskList) {
            // todoItem 去重
            BizTodoItem todoItem = bizTodoItemMapper.selectTodoItemByTaskId(task.getId());
            if (todoItem != null)
                continue;

            BizTodoItem newItem = new BizTodoItem();
            BeanUtils.copyProperties(bizTodoItem, newItem);
            newItem.setTaskId(task.getId());
            newItem.setTaskName("task" + StringUtils.firstLetterToUppercase(task.getProcessDefinitionId()));
            newItem.setNodeName(task.getName());

            // 处理用户及群组 assignee 不为空则 assignee为优先级最高
            String assignee = task.getAssignee();
            if (StringUtils.isNotBlank(assignee)) {
                newItem.setTodoUserId(assignee);
                UmsAdmin umsAdmin = umsAdminService.get(assignee);
                newItem.setTodoUserName(umsAdmin.getNickName());
                bizTodoItemMapper.insertBizTodoItem(newItem);
                count++;
            } else {
                // 查询用户候选组
                String groupId = bizTodoItemMapper.selectTodoGroupByTaskId(task.getId());
                if (StringUtils.isNotBlank(groupId)) {
                    List<UmsAdmin> umsAdminList = umsAdminService.getListByGroupId(groupId);
                    if (!CollectionUtils.isEmpty(umsAdminList)) {
                        for (UmsAdmin umsAdmin : umsAdminList) {
                            newItem.setTodoUserId(umsAdmin.getUsername());
                            newItem.setTodoUserName(umsAdmin.getNickName());
                            bizTodoItemMapper.insertBizTodoItem(newItem);
                            count++;
                        }
                    }
                } else {
                    // 查询候选用户
                    String username = bizTodoItemMapper.selectTodoUserByTaskId(task.getId());
                    UmsAdmin umsAdmin = umsAdminService.get(username);
                    newItem.setTodoUserId(username);
                    newItem.setTodoUserName(umsAdmin.getNickName());
                    bizTodoItemMapper.insertBizTodoItem(newItem);
                    count++;
                }
            }
        }
        return count;
    }

    @Override
    public BizTodoItem selectBizTodoItemByCondition(String taskId, String todoUserId) {
        return bizTodoItemMapper.selectTodoItemByCondition(taskId, todoUserId);
    }
}
