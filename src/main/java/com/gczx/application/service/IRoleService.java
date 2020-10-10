package com.gczx.application.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gczx.application.entity.RoleEntity;

public interface IRoleService extends IService<RoleEntity> {
    RoleEntity getRoleByName(String roleName);
}
