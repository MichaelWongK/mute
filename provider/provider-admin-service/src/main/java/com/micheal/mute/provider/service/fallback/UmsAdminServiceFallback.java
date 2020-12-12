package com.micheal.mute.provider.service.fallback;

import com.micheal.mute.provider.domain.UmsAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:wangmk13@163.com">micheal.wang</a>
 * @date 2020/11/25 14:51
 * @Description 用户服务提供者熔断器
 */
public class UmsAdminServiceFallback {

    private static final Logger logger = LoggerFactory.getLogger(UmsAdminServiceFallback.class);


    public static UmsAdmin getByUsernameFallback(String username, Throwable ex) {
        logger.warn("invoke getByUsernameFallback: " + ex.getClass().getTypeName());
        return null;
    }
}
