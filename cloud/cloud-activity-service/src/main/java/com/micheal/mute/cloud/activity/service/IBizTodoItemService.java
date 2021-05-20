package com.micheal.mute.cloud.activity.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.micheal.mute.cloud.activity.domain.BizTodoItem;
import com.micheal.mute.commons.page.PageDomain;

import java.util.List;

/**
 * @author <a href="mailto:wangmk13@163.com">micheal.wang</a>
 * @date 2021/5/17 14:55
 * @Description 待办事项 Service
 */
public interface IBizTodoItemService {

    /**
     * 查询待办事项
     *
     * @param id
     * @return
     */
    BizTodoItem selectBizTodoItemById(Long id);

    /**
     * 查询待办事项列表
     *
     * @param bizTodoItem
     * @return
     */
    IPage<BizTodoItem> selectBizTodoItemPage(BizTodoItem bizTodoItem, PageDomain pageDomain);

    /**
     * 查询待办事项列表
     *
     * @param bizTodoItem
     * @return
     */
    List<BizTodoItem> selectBizTodoItemList(BizTodoItem bizTodoItem);

    /**
     * 新增待办事项列
     *
     * @param bizTodoItem
     * @return
     */
    int insertBizTodoItem(BizTodoItem bizTodoItem);

    /**
     * 修改待办事项
     *
     * @param bizTodoItem
     * @return
     */
    int updateBizTodoItem(BizTodoItem bizTodoItem);

    /**
     * 批量删除待办事项
     *
     * @param ids 待删除待办事项ids
     * @return
     */
    int deleteBizTodoItemByIds(String[] ids);

    /**
     * 删除待办事项信息
     *
     * @param id 待办事项id
     * @return
     */
    int deleteBizTodoItemById(String id);

    /**
     * 处理下一步任务待办人
     *
     * @param instanceId 流程实例id
     * @param itemName 发起流程标题
     * @param itemContent 发起流程原因
     * @param module 所属模块 如 leave
     * @return
     */
    int insertTodoItem(String instanceId, String itemName, String itemContent, String module);

    BizTodoItem selectBizTodoItemByCondition(String taskId, String todoUserId);
}
