package com.micheal.mute.cloud;

import com.micheal.mute.cloud.message.MessageSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.stream.annotation.EnableBinding;

/**
 * @author <a href="mailto:wangmk13@163.com">micheal.wang</a>
 * @date 2020/11/27 15:19
 * @Description
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableBinding({MessageSource.class})
public class CloudMessageApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudMessageApplication.class, args);
    }
}
