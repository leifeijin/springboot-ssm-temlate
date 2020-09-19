package com.gczx.application.common.exception;

import cn.hutool.http.HttpStatus;

/**
 * 业务异常父类
 * @author kuangdebao
 */
public class BaseBusinessException extends RuntimeException {

    private final Integer code;

    /**
     * 根据响应枚举构造类
     * @param responseCodeEnum 响应枚举
     */
    public BaseBusinessException(ResponseCodeEnum responseCodeEnum) {
        this(responseCodeEnum.getMessage(), responseCodeEnum.getCode());
    }

    /**
     * 根据message构造类
     * @param message 异常消息
     */
    public BaseBusinessException(String message) {
        this(message, HttpStatus.HTTP_UNAUTHORIZED);
    }

    private BaseBusinessException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}