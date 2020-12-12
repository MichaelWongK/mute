package com.micheal.mute.provider.service;

import com.micheal.mute.provider.mapper.UmsAdminLoginLogMapper;
import com.micheal.mute.provider.api.UmsAdminLoginLogService;
import com.micheal.mute.provider.domain.UmsAdminLoginLog;
import org.apache.dubbo.config.annotation.Service;

import javax.annotation.Resource;

/**
 * @author <a href="mailto:wangmk13@163.com">micheal.wang</a>
 * @date 2020/11/26 17:51
 * @Description
 */
@Service(version = "1.0.0")
public class UmsAdminLoginLogServiceImpl implements UmsAdminLoginLogService {

    @Resource
    private UmsAdminLoginLogMapper umsAdminLoginLogMapper;

    @Override
    public int insert(UmsAdminLoginLog umsAdminLoginLog) {
        return umsAdminLoginLogMapper.insert(umsAdminLoginLog);
    }
}
