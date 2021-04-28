package com.gczx.application.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gczx.application.entity.RoleEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IRoleMapper extends BaseMapper<RoleEntity> {
}
