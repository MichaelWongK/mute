package com.micheal.mute.provider.api;

import com.micheal.mute.provider.domain.UmsAdminLoginLog;

/**
 * @author <a href="mailto:wangmk13@163.com">micheal.wang</a>
 * @date 2020/11/26 17:49
 * @Description  系统登录日志服务
 */
public interface UmsAdminLoginLogService {

    /**
     * 新增日志
     *
     * @param umsAdminLoginLog {@link UmsAdminLoginLog}
     * @return {@code int} 大于 0 则表示添加成功
     */
    int insert(UmsAdminLoginLog umsAdminLoginLog);
}
