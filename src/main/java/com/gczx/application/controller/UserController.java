package com.gczx.application.controller;

import com.gczx.application.common.JsonResult;
import com.gczx.application.common.exception.ResponseCodeEnum;
import com.gczx.application.common.exception.UserException;
import com.gczx.application.common.utils.CasbinUtils;
import com.gczx.application.controller.dto.UserGetDto;
import com.gczx.application.entity.RoleEntity;
import com.gczx.application.entity.UserEntity;
import com.gczx.application.service.IRoleService;
import com.gczx.application.service.IUserService;
import io.swagger.annotations.*;
import org.casbin.jcasbin.main.Enforcer;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author leifeijin
 */
@Api(tags = "用户控制器")
@RestController
public class UserController {
    @Resource
    private IUserService userService;
    @Resource
    private IRoleService roleService;
    @Resource
    private Enforcer enforcer;

    /**
     * 用户登录
     * @return 返回用户基本信息
     */
    @ApiOperation(value = "用户登录接口")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "用户登录成功"),
            @ApiResponse(code = 201, message = "用户登录失败"),
    })
    @GetMapping("/login")
    public JsonResult<UserEntity> userLogin(UserGetDto userGetDto) {
        UserEntity userEntity = userService.getUserByNameAndPassword(userGetDto.getName(), userGetDto.getPassword());
        if (null != userEntity) {
            return JsonResult.success(userEntity);
        } else {
            throw new UserException(ResponseCodeEnum.LOGIN_ERROR);
        }
    }

    /**
     * 用户添加
     * @return 返回用户基本信息
     */
    @ApiOperation(value = "用户添加接口")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "用户添加成功"),
            @ApiResponse(code = 201, message = "用户添加失败"),
    })
    @GetMapping("/user/get")
    public JsonResult<List<RoleEntity>> userGet(Long userId) {
        UserEntity userEntity = userService.getById(userId);
        List<String> roleNames = enforcer.getRolesForUserInDomain(userEntity.getName(), "vpgz-smp");
        List<RoleEntity> roles = new ArrayList<>();
        for (String roleName : roleNames) {
            String replace = roleName.replace("role:", "");
            roles.add(roleService.getRoleByName(replace));
        }
        return JsonResult.success(roles);
    }

    /**
     * 用户添加
     * @return 返回用户基本信息
     */
    @ApiOperation(value = "用户添加接口")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "用户添加成功"),
            @ApiResponse(code = 201, message = "用户添加失败"),
    })
    @PostMapping("/user/add")
    public JsonResult<UserEntity> userAdd(String name, String displayName, Long roleId) {
        UserEntity userEntity = userService.addUser(name, displayName, roleId);
        return JsonResult.success(userEntity);
    }

    /**
     * 用户删除
     * @return 返回用户基本信息
     */
    @ApiOperation(value = "用户删除接口")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "用户删除成功"),
            @ApiResponse(code = 201, message = "用户删除失败"),
    })
    @DeleteMapping("/user/del")
    public JsonResult<UserEntity> userDel(Long userId) {
        userService.delUser(userId);
        return JsonResult.success(null);
    }

    /**
     * 用户测试
     * @return String
     */
    @ApiImplicitParam(name = "token", paramType = "header", dataType = "String", required = true)
    @GetMapping("/test")
    public JsonResult<String> userTest() {

        return JsonResult.success("测试成功");
    }
}
