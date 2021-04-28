package com.gczx.application.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gczx.application.entity.RoleEntity;
import com.gczx.application.service.IRoleService;
import com.gczx.application.dao.IRoleMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: leifeijin
 * @Date: 2020/9/24
 * @Description:
 */
@Service
public class RoleServiceImpl extends ServiceImpl<IRoleMapper, RoleEntity> implements IRoleService {
    @Resource
    private IRoleMapper roleMapper;

    @Override
    public RoleEntity getRoleByName(String roleName) {
        QueryWrapper<RoleEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("name", roleName);
        return roleMapper.selectOne(wrapper);
    }
}
