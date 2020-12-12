package com.micheal.mute.cloud.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author <a href="mailto:wangmk13@163.com">micheal.wang</a>
 * @date 2020/11/27 11:15
 * @Description
 */
@Data
public class UmsAdminLoginLogDTO implements Serializable {


    private static final long serialVersionUID = 8401876909676133495L;

    private Long id;

    private Long adminId;

    private Date createTime;

    private String ip;

    private String address;

    /**
     * 浏览器登录类型
     */
    private String userAgent;
}
