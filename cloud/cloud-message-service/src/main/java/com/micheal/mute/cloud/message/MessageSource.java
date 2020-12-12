package com.micheal.mute.cloud.message;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * @author <a href="mailto:wangmk13@163.com">micheal.wang</a>
 * @date 2020/11/27 15:53
 * @Description
 */
public interface MessageSource {

    @Output("admin-login-log-topic")
    MessageChannel adminLoginLog();
}
