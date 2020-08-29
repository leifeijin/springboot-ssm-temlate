package com.gczx.application.service.impl;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gczx.application.entity.UserEntity;
import com.gczx.application.service.IUserService;
import com.gczx.application.service.dao.IUserMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl extends ServiceImpl<IUserMapper, UserEntity> implements IUserService {
    @Resource
    private IUserMapper userMapper;

    @Override
    public UserEntity getUserByNameAndPassword(String name, String password) {
        QueryWrapper<UserEntity> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(name)) {
            wrapper.eq("name", name);
        }
        if (StringUtils.isNotBlank(password)) {
            wrapper.eq("password", SecureUtil.md5(password));
        }
        return userMapper.selectOne(wrapper);
    }
}