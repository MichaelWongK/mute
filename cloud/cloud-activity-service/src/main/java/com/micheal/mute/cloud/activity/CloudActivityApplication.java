package com.micheal.mute.cloud.activity;

import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 * @author <a href="mailto:wangmk13@163.com">micheal.wang</a>
 * @date 2021/4/21 14:29
 * @Description
 */
@SpringBootApplication(exclude =
        {
            SecurityAutoConfiguration.class
        })
@EnableDiscoveryClient
@EnableResourceServer
public class CloudActivityApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudActivityApplication.class, args);
    }
}
