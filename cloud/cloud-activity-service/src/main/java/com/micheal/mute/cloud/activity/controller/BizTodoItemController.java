package com.micheal.mute.cloud.activity.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.micheal.mute.cloud.activity.domain.BizTodoItem;
import com.micheal.mute.cloud.activity.service.IBizTodoItemService;
import com.micheal.mute.commons.page.PageDomain;
import com.micheal.mute.commons.provider.controller.BaseController;
import com.micheal.mute.commons.provider.domain.AjaxResult;
import org.aspectj.weaver.loadtime.Aj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author <a href="mailto:wangmk13@163.com">micheal.wang</a>
 * @date 2021/5/20 15:07
 * @Description 待办事项controller
 */
@RestController
@RequestMapping("/todoItem")
public class BizTodoItemController extends BaseController {

    @Autowired
    private IBizTodoItemService bizTodoItemService;

    @PostMapping("/list")
    public AjaxResult listPage(BizTodoItem bizTodoItem, String username, PageDomain pageDomain) {
        bizTodoItem.setIsHandle("0");
        bizTodoItem.setTodoUserId(username);
        IPage<BizTodoItem> bizTodoItems = bizTodoItemService.selectBizTodoItemPage(bizTodoItem, pageDomain);
        return AjaxResult.success(bizTodoItems);
    }
}
