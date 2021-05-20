package com.micheal.mute.provider.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.micheal.mute.provider.domain.UmsAdmin;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UmsAdminMapper extends BaseMapper<UmsAdmin> {
    int deleteByPrimaryKey(Long id);

    int insert(UmsAdmin record);

    int insertSelective(UmsAdmin record);

    UmsAdmin selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UmsAdmin record);

    int updateByPrimaryKey(UmsAdmin record);

    @Select("select user.* from ums_admin user, ums_user_group uug where user.username = uug.USER_ID and uug.GROUP_ID = 'hr'")
    @ResultMap(value = "BaseResultMap")
    List<UmsAdmin> selectUsersByGroupId(String groupId);

    @Select("select uug.group_id from ums_admin user, ums_user_group uug where user.username = uug.USER_ID and user.username = #{username}")
    List<String> selectGroupsByUsername(String username);
}