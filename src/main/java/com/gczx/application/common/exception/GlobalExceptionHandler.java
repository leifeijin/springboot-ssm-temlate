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

    @ExceptionHandler({BaseBusinessException.class})
    public JsonResult<Object> handleBusinessException(BaseBusinessException e) {
        log.error(e.getMessage());
        return JsonResult.error(e.getMessage(), e.getCode());
    }

    @ExceptionHandler({Exception.class})
    public JsonResult<Object> handleGlobalException(Exception e) {
        log.error(e.getMessage(), e);
        return JsonResult.error(e.getMessage());
    }
}