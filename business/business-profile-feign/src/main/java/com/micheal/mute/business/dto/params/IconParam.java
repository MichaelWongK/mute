package com.micheal.mute.business.dto.params;

import lombok.Data;

import java.io.Serializable;

/**
 * @author <a href="mailto:wangmk13@163.com">micheal.wang</a>
 * @date 2020/11/24 11:13
 * @Description 修改头像参数
 */
@Data
public class IconParam implements Serializable {

    /**
     * 用户名
     */
    private String username;

    /**
     * 头像地址
     */
    private String path;

}