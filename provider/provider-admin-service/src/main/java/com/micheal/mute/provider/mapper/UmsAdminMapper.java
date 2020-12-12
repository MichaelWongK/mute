package com.micheal.mute.provider.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.micheal.mute.provider.domain.UmsAdmin;

public interface UmsAdminMapper extends BaseMapper<UmsAdmin> {
    int deleteByPrimaryKey(Long id);

    int insert(UmsAdmin record);

    int insertSelective(UmsAdmin record);

    UmsAdmin selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UmsAdmin record);

    int updateByPrimaryKey(UmsAdmin record);
}