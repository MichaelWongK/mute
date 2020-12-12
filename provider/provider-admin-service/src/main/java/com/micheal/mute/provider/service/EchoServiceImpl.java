package com.micheal.mute.provider.service;

import com.micheal.mute.provider.api.EchoService;
import org.apache.dubbo.config.annotation.Service;

/**
 * @author <a href="mailto:wangmk13@163.com">micheal.wang</a>
 * @date 2020/11/19 14:11
 * @Description
 */
@Service(version = "1.0.0")
public class EchoServiceImpl implements EchoService {
    @Override
    public String echo(String str) {
        return "hello dubbo " + str;
    }
}
