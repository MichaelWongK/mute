package com.micheal.mute.provider.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * ums_admin_login_log
 * @author  系统登录日志
 */
@Table(name="ums_admin_login_log")
@Data
public class UmsAdminLoginLog implements Serializable {
    private static final long serialVersionUID = 3737146169654209761L;

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Long id;

    @Column(name = "admin_id")
    private Long adminId;

    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "ip")
    private String ip;

    @Column(name = "address")
    private String address;

    /**
     * 浏览器登录类型
     */
    @Column(name = "user_agent")
    private String userAgent;
}