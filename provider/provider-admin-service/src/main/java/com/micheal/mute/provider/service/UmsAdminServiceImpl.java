package com.micheal.mute.provider.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.micheal.mute.provider.mapper.UmsAdminMapper;
import com.micheal.mute.provider.service.fallback.UmsAdminServiceFallback;
import com.micheal.mute.provider.api.UmsAdminService;
import com.micheal.mute.provider.domain.UmsAdmin;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author <a href="mailto:wangmk13@163.com">micheal.wang</a>
 * @date 2020/11/19 17:06
 * @Description
 */
@Service(version = "1.0.0")
public class UmsAdminServiceImpl implements UmsAdminService {

    @Resource
    private UmsAdminMapper umsAdminMapper;

    @Resource
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public int insert(UmsAdmin umsAdmin) {
        // 初始化用户对象
        initUmsAdmin(umsAdmin);
        return umsAdminMapper.insert(umsAdmin);
    }


    @Override
    @SentinelResource(value = "getByUsername", fallback = "getByUsernameFallback", fallbackClass = UmsAdminServiceFallback.class)
    public UmsAdmin get(String username) {
        QueryWrapper<UmsAdmin> queryWrapper = new QueryWrapper();
        queryWrapper.eq("username", username);
        return umsAdminMapper.selectOne(queryWrapper);
    }

    @Override
    public UmsAdmin get(UmsAdmin umsAdmin) {
        return umsAdminMapper.selectOne(new QueryWrapper<UmsAdmin>().eq("username", umsAdmin.getUsername()));
    }

    @Override
    public int update(UmsAdmin umsAdmin) {
        UmsAdmin oldAdmin = get(umsAdmin.getUsername());

        oldAdmin.setEmail(umsAdmin.getEmail());
        oldAdmin.setNickName(umsAdmin.getNickName());
        oldAdmin.setNote(umsAdmin.getNote());
        oldAdmin.setStatus(umsAdmin.getStatus());

        return umsAdminMapper.updateByPrimaryKeySelective(umsAdmin);
    }

    @Override
    public int modifyPassword(String username, String password) {
        UmsAdmin umsAdmin = get(username);
        umsAdmin.setPassword(passwordEncoder.encode(password));
        return umsAdminMapper.updateByPrimaryKeySelective(umsAdmin);
    }

    @Override
    public int modifyIcon(String username, String path) {
        UmsAdmin umsAdmin = get(username);
        umsAdmin.setIcon(path);
        return umsAdminMapper.updateByPrimaryKeySelective(umsAdmin);
    }

    /**
     * 初始化用户对象
     * @param umsAdmin {@link UmsAdmin}
     */
    private void initUmsAdmin(UmsAdmin umsAdmin) {
        // 初始化创建时间
        umsAdmin.setCreateTime(new Date());
        umsAdmin.setLoginTime(new Date());

        // 初始化状态
        if (umsAdmin.getStatus() == null) {
            umsAdmin.setStatus(0);
        }

        // 密码加密
        umsAdmin.setPassword(passwordEncoder.encode(umsAdmin.getPassword()));
    }
}
