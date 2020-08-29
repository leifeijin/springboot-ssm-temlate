package com.gczx.application.common.exception;

/**
 * 自定义用户异常
 * @author kuangdebao
 */
public class UserException extends BaseBusinessException { // 继承 业务异常类
    public UserException(ResponseCodeEnum responseCodeEnum) {
        super(responseCodeEnum);
    }
}
