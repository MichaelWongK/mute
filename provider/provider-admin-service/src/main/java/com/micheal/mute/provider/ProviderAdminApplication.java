package com.micheal.mute.provider;

import com.micheal.mute.configuration.DubboSentinelConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author <a href="mailto:wangmk13@163.com">micheal.wang</a>
 * @date 2020/11/19 11:17
 * @Description
 */
@SpringBootApplication(scanBasePackageClasses = {ProviderAdminApplication.class, DubboSentinelConfiguration.class})
@MapperScan(basePackages = "com.micheal.mute.provider.mapper")
public class ProviderAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProviderAdminApplication.class, args);
    }
}
