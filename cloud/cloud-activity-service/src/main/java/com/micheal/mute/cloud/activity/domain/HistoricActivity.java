package com.micheal.mute.cloud.activity.domain;

import org.activiti.engine.impl.persistence.entity.HistoricActivityInstanceEntityImpl;

/**
 * @author <a href="mailto:wangmk13@163.com">micheal.wang</a>
 * @date 2021/5/11 14:50
 * @Description
 */
public class HistoricActivity extends HistoricActivityInstanceEntityImpl {

    /** 审批批注 */
    private String comment;
    /** 办理人姓名 */
    private String assigneeName;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getAssigneeName() {
        return assigneeName;
    }

    public void setAssigneeName(String assigneeName) {
        this.assigneeName = assigneeName;
    }
}
