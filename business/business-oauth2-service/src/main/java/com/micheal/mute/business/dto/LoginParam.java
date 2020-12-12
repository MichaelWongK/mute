package com.micheal.mute.business.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author <a href="mailto:wangmk13@163.com">micheal.wang</a>
 * @date 2020/11/20 17:54
 * @Description 登录参数
 */
@Data
public class LoginParam implements Serializable {

    private String username;
    private String password;
}
