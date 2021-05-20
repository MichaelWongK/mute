package com.micheal.mute.provider;
import java.util.Date;

import com.micheal.mute.provider.api.UmsAdminService;
import com.micheal.mute.provider.domain.UmsAdmin;
import com.micheal.mute.provider.mapper.UmsAdminMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author <a href="mailto:wangmk13@163.com">micheal.wang</a>
 * @date 2020/11/19 16:08
 * @Description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UmsAdminTest {

    @Autowired
    private UmsAdminMapper umsAdminMapper;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private UmsAdminService umsAdminService;

    @Test
    public void testSelectAll() {
        UmsAdmin umsAdmin = umsAdminMapper.selectById(1);
        System.out.println(umsAdmin);
    }

    @Test
    public void testInsert(){
        UmsAdmin umsAdmin = new UmsAdmin();
        umsAdmin.setUsername("micheal");
        umsAdmin.setPassword(passwordEncoder.encode("123456"));
        umsAdmin.setIcon("");
        umsAdmin.setEmail("");
        umsAdmin.setNickName("");
        umsAdmin.setNote("");
        umsAdmin.setCreateTime(new Date());
        umsAdmin.setLoginTime(new Date());
        umsAdmin.setStatus(0);

        int result = umsAdminService.insert(umsAdmin);
        Assert.assertEquals(result, 1);
    }

    @Test
    public void testSelect() {
        System.out.println(umsAdminMapper.selectUsersByGroupId("hr"));;
    }
}
