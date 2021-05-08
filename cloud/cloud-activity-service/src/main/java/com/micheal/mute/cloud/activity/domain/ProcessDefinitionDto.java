package com.micheal.mute.cloud.activity.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.micheal.mute.commons.provider.domain.BaseEntity;

import java.util.Date;

/**
 * @author <a href="mailto:wangmk13@163.com">micheal.wang</a>
 * @date 2021/5/6 18:22
 * @Description
 */
public class ProcessDefinitionDto extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String id;

    /** 流程名称 */
    private String name;

    /** 流程KEY */ 
    private String key;

    /** 流程版本 */ 
    private int version;

    /** 所属分类 */ 
    private String category;

    /** 流程描述 */ 
    private String description;

    private String deploymentId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    /** 部署时间 */ 
    private Date deploymentTime;

    /** 流程图 */ 
    private String diagramResourceName;

    /** 流程定义 */ 
    private String resourceName;

    /** 流程实例状态 1 激活 2 挂起 */
    private String suspendState;

    private String suspendStateName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDeploymentId() {
        return deploymentId;
    }

    public void setDeploymentId(String deploymentId) {
        this.deploymentId = deploymentId;
    }

    public Date getDeploymentTime() {
        return deploymentTime;
    }

    public void setDeploymentTime(Date deploymentTime) {
        this.deploymentTime = deploymentTime;
    }

    public String getDiagramResourceName() {
        return diagramResourceName;
    }

    public void setDiagramResourceName(String diagramResourceName) {
        this.diagramResourceName = diagramResourceName;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getSuspendState() {
        return suspendState;
    }

    public void setSuspendState(String suspendState) {
        this.suspendState = suspendState;
    }

    public String getSuspendStateName() {
        return suspendStateName;
    }

    public void setSuspendStateName(String suspendStateName) {
        this.suspendStateName = suspendStateName;
    }
}
