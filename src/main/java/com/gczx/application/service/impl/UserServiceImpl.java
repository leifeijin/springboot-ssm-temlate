package com.gczx.application.service.impl;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gczx.application.common.utils.CasbinUtils;
import com.gczx.application.entity.RoleEntity;
import com.gczx.application.entity.UserEntity;
import com.gczx.application.service.IRoleService;
import com.gczx.application.service.IUserService;
import com.gczx.application.service.dao.IUserMapper;
import org.apache.commons.lang3.StringUtils;
import org.casbin.jcasbin.main.Enforcer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class UserServiceImpl extends ServiceImpl<IUserMapper, UserEntity> implements IUserService {
    @Resource
    private IUserMapper userMapper;
    @Resource
    private IRoleService roleService;
    @Resource
    private Enforcer enforcer;

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

    @Override
    @Transactional
    public UserEntity addUser(String name, String displayName, Long roleId) {
        UserEntity userEntity = new UserEntity();
        userEntity.setName(name);
        userEntity.setPassword(SecureUtil.md5("123456"));
        userEntity.setDisplayName(displayName);
        userMapper.insert(userEntity);
        RoleEntity role = roleService.getById(roleId);
        enforcer.addGroupingPolicy(name, "role:" + role.getName(), "vpgz-smp");
//        CasbinUtils.addGroupingPolicy(name, "role:" + role.getName(), "vpgz-smp");
        return userEntity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delUser(Long userId) {
        UserEntity userEntity = userMapper.selectById(userId);
        userMapper.deleteById(userId);
        enforcer.deleteUser(userEntity.getName());
//        CasbinUtils.deleteUser(userEntity.getName());
    }
}
