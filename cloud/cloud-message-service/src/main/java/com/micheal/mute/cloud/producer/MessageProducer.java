package com.micheal.mute.cloud.producer;

import com.micheal.mute.cloud.dto.UmsAdminLoginLogDTO;
import com.micheal.mute.cloud.message.MessageSource;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author <a href="mailto:wangmk13@163.com">micheal.wang</a>
 * @date 2020/11/27 15:54
 * @Description
 */
@Service
public class MessageProducer {

    @Resource
    private MessageSource source;

    public boolean sendAdminLoginLog(UmsAdminLoginLogDTO dto) {
        return source.adminLoginLog().send(MessageBuilder.withPayload(dto).build());
    }
}
