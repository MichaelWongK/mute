package com.micheal.mute.cloud.activity.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.micheal.mute.cloud.activity.domain.BizTodoItem;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author <a href="mailto:wangmk13@163.com">micheal.wang</a>
 * @date 2021/5/17 15:20
 * @Description 待办事项Mapper接口
 */
@Repository
public interface BizTodoItemMapper {

    /**
     * 查询待办事项
     *
     * @param id 待办事项ID
     * @return 待办事项
     */
    public BizTodoItem selectBizTodoItemById(Long id);

    /**
     * 查询待办事项列表分页
     *
     * @param bizTodoItem 待办事项
     * @return 待办事项集合
     */
    public IPage<BizTodoItem> selectBizTodoItemPage(IPage<BizTodoItem> page, @Param("bizTodoItem") BizTodoItem bizTodoItem);

    /**
     * 查询待办事项列表
     *
     * @param bizTodoItem 待办事项
     * @return 待办事项集合
     */
    public List<BizTodoItem> selectBizTodoItemList(BizTodoItem bizTodoItem);

    /**
     * 新增待办事项
     *
     * @param bizTodoItem 待办事项
     * @return 结果
     */
    public int insertBizTodoItem(BizTodoItem bizTodoItem);

    /**
     * 修改待办事项
     *
     * @param bizTodoItem 待办事项
     * @return 结果
     */
    public int updateBizTodoItem(BizTodoItem bizTodoItem);

    /**
     * 删除待办事项
     *
     * @param id 待办事项ID
     * @return 结果
     */
    public int deleteBizTodoItemById(Long id);

    /**
     * 批量删除待办事项
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBizTodoItemByIds(String[] ids);

    /**
     * 查询待办事项详情
     *
     * @param taskId 任务id
     * @return
     */
    @Select("SELECT * FROM BIZ_TODO_ITEM WHERE TASK_ID = #{taskId}")
    BizTodoItem selectTodoItemByTaskId(@Param(value = "taskId") String taskId);

    /**
     * 通过任务id查询 groupId 再通过groupId查询group下所有用户集合
     * @param taskId
     * @return
     */
    @Select("SELECT GROUP_ID_ FROM ACT_RU_IDENTITYLINK WHERE TASK_ID_ = #{taskId}")
    String selectTodoGroupByTaskId(@Param(value = "taskId") String taskId);

    /**
     * 查询待办事项
     *
     * @param taskId
     * @param todoUserId
     * @return
     */
    @Select("SELECT * FROM BIZ_TODO_ITEM WHERE TASK_ID = #{taskId} AND TODO_USER_ID = #{todoUserId}")
    BizTodoItem selectTodoItemByCondition(@Param(value = "taskId") String taskId, @Param(value = "todoUserId") String todoUserId);

    /**
     * 通过任务id查询 user 再通过 userId 查询 是否存在该关系下 userId
     * @param id
     * @return
     */
    @Select("SELECT USER_ID_ FROM ACT_RU_IDENTITYLINK WHERE TASK_ID_ = #{taskId}")
    String selectTodoUserByTaskId(String id);
}
