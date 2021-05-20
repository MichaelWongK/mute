package com.micheal.mute.cloud.activity.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.micheal.mute.cloud.activity.domain.BizTodoItem;
import com.micheal.mute.cloud.activity.service.IBizTodoItemService;
import com.micheal.mute.provider.api.UmsAdminService;
import org.apache.dubbo.config.annotation.Reference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * @author <a href="mailto:wangmk13@163.com">micheal.wang</a>
 * @date 2021/5/18 10:51
 * @Description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BizTodoItemMapperTest {

    @Autowired
    private BizTodoItemMapper bizTodoItemMapper;
    @Autowired
    private IBizTodoItemService bizTodoItemService;
    @Reference(version = "1.0.0")
    private UmsAdminService umsAdminService;


    @Test
    public void testSelectBizTodoItemPage() {
        Page<BizTodoItem> page = new Page(2,10, true);
        BizTodoItem bizTodoItem = new BizTodoItem();
        bizTodoItem.setTodoUserId("axianlu");
        IPage<BizTodoItem> bizTodoItemIPage = bizTodoItemMapper.selectBizTodoItemPage(page, bizTodoItem);
        System.out.println(bizTodoItemIPage);
    }

    @Test
    public void testSelectTodoItemByTaskId() {
        System.out.println("todoItem: " + bizTodoItemMapper.selectTodoItemByTaskId("22507"));
    }

    @Test
    public void testSelectTodoGroupByTaskId() {
        System.out.println("toDoGroupId: " + bizTodoItemMapper.selectTodoGroupByTaskId("22507"));;
    }

    @Test
    public void testSelectTodoUserByTaskId() {
        System.out.println("toDoUserId: " + bizTodoItemMapper.selectTodoUserByTaskId("5012"));;
    }

    @Test
    public void testInsertTodoItem() {
        System.out.println(umsAdminService.get("axianlu"));;
    }
}
