package com.gczx.application.controller;

import com.gczx.application.common.JsonResult;
import com.gczx.application.common.exception.ResponseCodeEnum;
import com.gczx.application.common.exception.UserException;
import com.gczx.application.controller.dto.UserGetDto;
import com.gczx.application.entity.UserEntity;
import com.gczx.application.service.IUserService;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api("用户控制器")
@RestController
public class UserController {
    @Resource
    private IUserService userService;

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
    public JsonResult userLogin(UserGetDto userGetDto) {
        UserEntity userEntity = userService.getUserByNameAndPassword(userGetDto.getName(), userGetDto.getPassword());
        if (null != userEntity) {
            return JsonResult.success(userEntity);
        } else {
            throw new UserException(ResponseCodeEnum.LOGIN_ERROR);
        }
    }

    /**
     * 用户测试
     * @return
     */
    @ApiImplicitParam(name = "token", paramType = "header", dataType = "String", required = true)
    @GetMapping("/test")
    public JsonResult userTest() {

        return JsonResult.success("测试成功");
    }
}
