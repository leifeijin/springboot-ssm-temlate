package com.gczx.application.common.exception;

import cn.hutool.http.HttpStatus;

/**
 * 业务异常父类
 * @author kuangdebao
 */
public class BaseBusinessException extends RuntimeException {

    private Integer code;

    // 给子类用的方法
    public BaseBusinessException(ResponseCodeEnum responseCodeEnum) {
        this(responseCodeEnum.getMessage(), responseCodeEnum.getCode());
    }
    // 给子类用的方法
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

    public void setCode(Integer code) {
        this.code = code;
    }
}