package com.gczx.application.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gczx.application.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author leifeijin
 */
@Mapper
public interface IUserMapper extends BaseMapper<UserEntity> {
}
