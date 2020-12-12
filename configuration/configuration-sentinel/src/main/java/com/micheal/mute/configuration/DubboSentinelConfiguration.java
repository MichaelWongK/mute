package com.micheal.mute.configuration;

import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author <a href="mailto:wangmk13@163.com">micheal.wang</a>
 * @date 2020/11/25 11:21
 * @Description
 */
@Configuration
public class DubboSentinelConfiguration {

    @Bean
    public SentinelResourceAspect sentinelResourceAspect() {
        return new SentinelResourceAspect();
    }
}
