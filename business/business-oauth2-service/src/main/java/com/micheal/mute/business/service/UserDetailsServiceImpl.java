package com.micheal.mute.business.service;

import com.google.common.collect.Lists;
import com.micheal.mute.provider.api.UmsAdminService;
import com.micheal.mute.provider.domain.UmsAdmin;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

/**
 * @author <a href="mailto:wangmk13@163.com">micheal.wang</a>
 * @date 2020/11/19 22:34
 * @Description
 */
@Configuration
public class UserDetailsServiceImpl implements UserDetailsService {

    @Reference(version = "1.0.0")
    private UmsAdminService umsAdminService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 用户名匹配
        List<GrantedAuthority> grantedAuthorities = Lists.newArrayList();
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("USER");
        grantedAuthorities.add(grantedAuthority);


        UmsAdmin umsAdmin = umsAdminService.get(username);

        // 账号存在
        if (umsAdmin != null) {
            return new User(username, umsAdmin.getPassword(), grantedAuthorities);
        }

        return null;
    }
}
