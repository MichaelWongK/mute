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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


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

    /**
     * 分页查询待办事项列表
     * @param bizTodoItem
     * @param username
     * @param pageDomain
     * @return
     */
    @PostMapping("/listPage")
    public AjaxResult listPage(BizTodoItem bizTodoItem, String username, PageDomain pageDomain) {
        bizTodoItem.setIsHandle("0");
        bizTodoItem.setTodoUserId(username);
        IPage<BizTodoItem> bizTodoItems = bizTodoItemService.selectBizTodoItemPage(bizTodoItem, pageDomain);
        return AjaxResult.success(bizTodoItems);
    }

    /**
     * 分页查询已办事项列表
     * @param bizTodoItem
     * @param username
     * @param pageDomain
     * @return
     */
    @PostMapping("/doneList")
    public AjaxResult doneList(BizTodoItem bizTodoItem, String username, PageDomain pageDomain) {
        bizTodoItem.setIsHandle("1");
        bizTodoItem.setTodoUserId(username);
        IPage<BizTodoItem> donePage = bizTodoItemService.selectBizTodoItemPage(bizTodoItem, pageDomain);
        return AjaxResult.success(donePage);
    }

    /**
     * 导出待办事项列表
     */
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(BizTodoItem bizTodoItem, String username) {
        bizTodoItem.setIsHandle("0");
        bizTodoItem.setTodoUserId(username);
        List<BizTodoItem> list = bizTodoItemService.selectBizTodoItemList(bizTodoItem);
        // TODO: 导出数据生成 excel
        return null;
    }

    /**
     * 导出已办事项列表
     */
    @PostMapping("/doneExport")
    public AjaxResult doneExport(BizTodoItem bizTodoItem, String username){
        bizTodoItem.setIsHandle("1");
        bizTodoItem.setTodoUserId(username);
        List<BizTodoItem> bizTodoItems = bizTodoItemService.selectBizTodoItemList(bizTodoItem);
        // TODO: 导出数据生成 excel
        return null;
    }
}
