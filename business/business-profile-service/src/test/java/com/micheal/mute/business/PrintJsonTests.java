package com.micheal.mute.business;

import com.micheal.mute.business.dto.params.ProfileParam;
import com.micheal.mute.commons.utils.MapperUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * @author <a href="mailto:wangmk13@163.com">micheal.wang</a>
 * @date 2020/11/23 11:30
 * @Description
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class PrintJsonTests {

    @Test
    public void testProfileParam() throws Exception {
        System.out.println(MapperUtils.obj2json(new ProfileParam()));
    }
}
