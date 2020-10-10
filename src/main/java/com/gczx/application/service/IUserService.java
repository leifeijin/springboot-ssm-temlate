package com.gczx.application.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gczx.application.entity.UserEntity;

public interface IUserService extends IService<UserEntity> {
    /**
     * 根据用户登录名和密码查询用户信息
     * @param name     用户登录名
     * @param password 用户登录密码
     * @return
     */
    UserEntity getUserByNameAndPassword(String name, String password);

    /**
     * 添加用户
     * @param name
     * @param displayName
     * @param roleId
     * @return
     */
    UserEntity addUser(String name, String displayName, Long roleId);

    /**
     * 删除用户信息
     * @param userId
     */
    void delUser(Long userId);
}
