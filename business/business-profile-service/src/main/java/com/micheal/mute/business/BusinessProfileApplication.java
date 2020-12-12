package com.micheal.mute.business;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author <a href="mailto:wangmk13@163.com">micheal.wang</a>
 * @date 2020/11/21 19:59
 * @Description
 */
@SpringBootApplication
@EnableDiscoveryClient
public class BusinessProfileApplication {

    public static void main(String[] args) {
        SpringApplication.run(BusinessProfileApplication.class, args);
    }
}
