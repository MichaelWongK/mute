package com.micheal.mute.business;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author <a href="mailto:wangmk13@163.com">micheal.wang</a>
 * @date 2020/11/19 22:21
 * @Description
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class BusinessOAuth2Application {

    public static void main(String[] args) {
        SpringApplication.run(BusinessOAuth2Application.class, args);
    }
}
