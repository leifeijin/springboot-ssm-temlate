package com.gczx.application.service.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gczx.application.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IUserMapper extends BaseMapper<UserEntity> {
}
