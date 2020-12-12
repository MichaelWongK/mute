package com.micheal.mute.business.feign;

import com.micheal.mute.business.dto.params.IconParam;
import com.micheal.mute.business.dto.params.PasswordParam;
import com.micheal.mute.business.dto.params.ProfileParam;
import com.micheal.mute.business.feign.fallback.ProfileFeignFallback;
import com.micheal.mute.configuration.FeignRequestConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author <a href="mailto:wangmk13@163.com">micheal.wang</a>
 * @date 2020/11/21 20:31
 * @Description
 */
@FeignClient(value = "business-profile", path = "profile", configuration = FeignRequestConfiguration.class, fallback = ProfileFeignFallback.class)
public interface ProfileFeign {

    /**
     * 获取个人信息
     *
     * @param username {@code String} 用户名
     * @return {@code String} JSON
     */
    @GetMapping(value = "info/{username}")
    String info(@PathVariable String username);

    /**
     * 更新个人信息
     *
     * @param profileParam {@link ProfileParam}
     * @return {@code String} JSON
     */
    @PostMapping(value = "update")
    String update(@RequestBody ProfileParam profileParam);

    /**
     * 修改密码
     *
     * @param passwordParam {@link PasswordParam}
     * @return {@code String} JSON
     */
    @PostMapping(value = "modify/password")
    String modifyPassword(@RequestBody PasswordParam passwordParam);

    /**
     * 修改头像
     *
     * @param iconParam {@link IconParam}
     * @return {@code String} JSON
     */
    @PostMapping(value = "modify/icon")
    String modifyIcon(@RequestBody IconParam iconParam);
}
