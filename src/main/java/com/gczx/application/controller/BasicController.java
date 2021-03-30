package com.gczx.application.controller;

import com.gczx.application.common.JsonResult;
import com.gczx.application.common.exception.ResponseCodeEnum;
import com.gczx.application.controller.dto.UserGetDto;
import com.gczx.application.entity.UserEntity;
import com.gczx.application.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author leifeijin
 */
@Api(tags = "基础通用")
@RestController
public class BasicController {
    @Resource
    private IUserService userService;
    @Resource
    private HttpServletRequest request;

    @ApiOperation(value = "用户登录")
    @PostMapping("/login")
    public JsonResult<UserEntity> userLogin(@RequestBody UserGetDto userGetDto) {
        UserEntity userEntity = userService.getUserByNameAndPassword(userGetDto.getName(), userGetDto.getPassword());
        return JsonResult.success(userEntity);
    }

    @ApiOperation(value = "用户登出")
    @PostMapping("/logout")
    public JsonResult<Object> userLogout() {
        HttpSession session = request.getSession();
        session.removeAttribute("sessionKey");
        session.invalidate();
        return JsonResult.success();
    }
}
