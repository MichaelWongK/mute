package com.micheal.mute.provider.tests;
import java.util.Date;

import com.micheal.mute.provider.api.UmsAdminLoginLogService;
import com.micheal.mute.provider.domain.UmsAdminLoginLog;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author <a href="mailto:wangmk13@163.com">micheal.wang</a>
 * @date 2020/11/26 17:57
 * @Description
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
@Rollback
public class UmsAdminLoginLogTest {

    @Resource
    private UmsAdminLoginLogService umsAdminLoginLogService;

    @Test
    public void testInsert() {
        UmsAdminLoginLog umsAdminLoginLog = new UmsAdminLoginLog();
        umsAdminLoginLog.setAdminId(1L);
        umsAdminLoginLog.setCreateTime(new Date());
        umsAdminLoginLog.setIp("0.0.0.0");
        umsAdminLoginLog.setAddress("0.0.0.0");
        umsAdminLoginLog.setUserAgent("0.0.0.0");

        umsAdminLoginLogService.insert(umsAdminLoginLog);
    }
}
