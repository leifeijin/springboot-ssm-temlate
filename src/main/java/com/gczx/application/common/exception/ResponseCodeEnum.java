package com.gczx.application.common.exception;
import lombok.Getter;
/**
 * 响应配置枚举
 * @author kuangdebao
 */
@Getter
public enum ResponseCodeEnum {
    // 系统通用
    SUCCESS(200, "成功!"),
    LOGIN_ERROR(401, "登录名或者密码不正确"),
    RTS_OPERATE_FAILURE(400,"操作失败!"),
    SIGNATURE_NOT_MATCH(403,"请求的数字签名不匹配!"),
    NOT_FOUND(404, "未找到该资源!"),
    INTERNAL_SERVER_ERROR(500, "服务器内部错误!"),
    SERVER_BUSY(503,"服务器正忙，请稍后再试!"),

    // 自定义业务异常
    GET_DEMO_INFO_FAILED(1001, "获取DEMO信息失败"),
    ADD_DEMO_INFO_FAILED(1002, "新增DEMO信息失败"),
    UPDATE_DEMO_INFO_FAILED(1003, "更新DEMO信息失败"),
    DELETE_DEMO_INFO_FAILED(1004, "删除DEMO信息失败");

    private Integer code;
    private String message;

    ResponseCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public final Integer getCode() {
        return this.code;
    }

    public final String getMessage() {
        return this.message;
    }
}
