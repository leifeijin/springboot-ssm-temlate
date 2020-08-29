package com.gczx.application.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(value = "UserGetDto", description = "用户登录")
public class UserGetDto {
    @ApiModelProperty(value = "用户登录名", dataType = "String",  required = true)
    private String name;
    @ApiModelProperty(value = "用户登录密码", dataType = "String", required = true)
    private String password;

}
