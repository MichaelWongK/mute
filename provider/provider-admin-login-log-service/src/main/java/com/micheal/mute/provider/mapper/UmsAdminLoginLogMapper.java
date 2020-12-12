package com.micheal.mute.provider.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.micheal.mute.provider.domain.UmsAdminLoginLog;

public interface UmsAdminLoginLogMapper extends BaseMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UmsAdminLoginLog record);

    int insertSelective(UmsAdminLoginLog record);

    UmsAdminLoginLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UmsAdminLoginLog record);

    int updateByPrimaryKey(UmsAdminLoginLog record);
}