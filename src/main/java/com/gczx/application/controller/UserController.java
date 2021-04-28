package com.gczx.application.controller;

import com.gczx.application.common.JsonResult;
import com.gczx.application.entity.RoleEntity;
import com.gczx.application.entity.UserEntity;
import com.gczx.application.service.IRoleService;
import com.gczx.application.service.IUserService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.annotations.*;
import org.casbin.jcasbin.main.Enforcer;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author leifeijin
 */
@Api(tags = "用户控制器")
@ApiSort(2)
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private IUserService userService;
    @Resource
    private IRoleService roleService;
    @Resource
    private Enforcer enforcer;

    @ApiOperation(value = "用户查询")
    @ApiOperationSupport(order = 1)
    @GetMapping("/get")
    public JsonResult<UserEntity> userGet(Long userId) {
        UserEntity userEntity = userService.getById(userId);
        List<String> roleNames = enforcer.getRolesForUserInDomain(userEntity.getName(), "vpgz-smp");
        List<RoleEntity> roles = new ArrayList<>();
        for (String roleName : roleNames) {
            String replace = roleName.replace("role:", "");
            roles.add(roleService.getRoleByName(replace));
        }
        return JsonResult.success(userEntity);
    }

    @ApiOperation(value = "用户添加")
    @ApiOperationSupport(order = 2)
    @PostMapping("/add")
    public JsonResult<UserEntity> userAdd(String name, String displayName, Long roleId) {
        UserEntity userEntity = userService.addUser(name, displayName, roleId);
        return JsonResult.success(userEntity);
    }

    @ApiOperation(value = "用户删除")
    @ApiOperationSupport(order = 3)
    @DeleteMapping("/delete")
    public JsonResult<UserEntity> userDel(Long userId) {
        userService.delUser(userId);
        return JsonResult.success();
    }
}
