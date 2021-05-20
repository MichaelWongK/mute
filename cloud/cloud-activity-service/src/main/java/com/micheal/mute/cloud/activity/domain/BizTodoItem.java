package com.micheal.mute.cloud.activity.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.micheal.mute.commons.provider.domain.BaseEntity;

import java.util.Date;

/**
 * @author <a href="mailto:wangmk13@163.com">micheal.wang</a>
 * @date 2021/5/11 15:08
 * @Description 待办事项entity biz_todo_item
 */
public class BizTodoItem extends BaseEntity {

    /** 主键 ID */
    private Long id;

    /** 事项标题 */
    private String itemName;

    /** 事项内容 */
    private String itemContent;

    /** 模块名称 (必须以 uri 一致) */
    private String module;

    /** 任务 ID */
    private String taskId;

    /** 流程实例 ID */
    private String instanceId;

    /** 任务名称 (必须以表单页面名称一致) */
    private String taskName;

    /** 节点名称 */
    private String nodeName;

    /** 是否查看 default 0 (0 否 1 是) */
    private String isView;

    /** 是否处理 default 0 (0 否 1 是) */
    private String isHandle;

    /** 待办人 ID */
    private String todoUserId;

    /** 待办人名称 */
    private String todoUserName;

    /** 处理人 ID */
    private String handleUserId;

    /** 处理人名称 */
    private String handleUserName;

    /** 通知时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date todoTime;

    /** 处理时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date handleTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemContent() {
        return itemContent;
    }

    public void setItemContent(String itemContent) {
        this.itemContent = itemContent;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getIsView() {
        return isView;
    }

    public void setIsView(String isView) {
        this.isView = isView;
    }

    public String getIsHandle() {
        return isHandle;
    }

    public void setIsHandle(String isHandle) {
        this.isHandle = isHandle;
    }

    public String getTodoUserId() {
        return todoUserId;
    }

    public void setTodoUserId(String todoUserId) {
        this.todoUserId = todoUserId;
    }

    public String getTodoUserName() {
        return todoUserName;
    }

    public void setTodoUserName(String todoUserName) {
        this.todoUserName = todoUserName;
    }

    public String getHandleUserId() {
        return handleUserId;
    }

    public void setHandleUserId(String handleUserId) {
        this.handleUserId = handleUserId;
    }

    public String getHandleUserName() {
        return handleUserName;
    }

    public void setHandleUserName(String handleUserName) {
        this.handleUserName = handleUserName;
    }

    public Date getTodoTime() {
        return todoTime;
    }

    public void setTodoTime(Date todoTime) {
        this.todoTime = todoTime;
    }

    public Date getHandleTime() {
        return handleTime;
    }

    public void setHandleTime(Date handleTime) {
        this.handleTime = handleTime;
    }
}
