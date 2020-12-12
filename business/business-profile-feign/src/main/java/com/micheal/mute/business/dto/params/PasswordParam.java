package com.micheal.mute.business.dto.params;

import lombok.Data;

import java.io.Serializable;

/**
 * @author <a href="mailto:wangmk13@163.com">micheal.wang</a>
 * @date 2020/11/24 11:12
 * @Description  修改密码参数
 */
@Data
public class PasswordParam implements Serializable {

    private String username;
    private String oldPassword;
    private String newPassword;

}