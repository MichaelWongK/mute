package com.micheal.mute.provider.configure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author <a href="mailto:wangmk13@163.com">micheal.wang</a>
 * @date 2020/11/19 17:15
 * @Description
 */
@Configuration
public class UmsAdminResourceConfiguration {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
