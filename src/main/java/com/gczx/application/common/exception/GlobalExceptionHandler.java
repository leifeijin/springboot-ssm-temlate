package com.gczx.application.common.exception;

import com.gczx.application.common.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 * @author kuangdebao
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    // 处理用户异常
    @ExceptionHandler({UserException.class})
    public JsonResult handleUserException(UserException e) {
        log.error(e.getMessage());
        return new JsonResult().error(e.getMessage(), e.getCode());
    }

    // 处理自定义的业务异常
    @ExceptionHandler({BaseBusinessException.class})
    public JsonResult handleBusinessException(BaseBusinessException e) {
        log.error(e.getMessage());
        return new JsonResult().error(e.getMessage(), e.getCode());
    }

    // 处理未知异常
    @ExceptionHandler({Exception.class})
    public JsonResult handleGlobalException(Exception e) {
        log.error(e.getMessage(), e);
        return new JsonResult().error(e.getMessage());
    }
}