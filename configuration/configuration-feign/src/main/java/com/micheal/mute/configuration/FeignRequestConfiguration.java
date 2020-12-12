package com.micheal.mute.configuration;

import com.micheal.mute.interceptor.FeignRequestInterceptor;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author <a href="mailto:wangmk13@163.com">micheal.wang</a>
 * @date 2020/11/21 21:34
 * @Description feignRequest全局配置
 */
@Configuration
public class FeignRequestConfiguration {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return new FeignRequestInterceptor();
    }
}
