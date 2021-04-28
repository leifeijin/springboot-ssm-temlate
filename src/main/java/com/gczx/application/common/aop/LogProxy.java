package com.gczx.application.common.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 日志AOP
 * @author leifeijin
 */
@Slf4j
@Aspect
@Component
public class LogProxy {
    /**
     * 定义切入点
     */
    @Pointcut(value = "execution(* com.gczx.application.controller.*.*(..))")
    public void pointCut() {

    }

    @Before(value = "pointCut()")
    public void before() {
        log.info("方法前执行前...");
    }

    @After(value = "pointCut()")
    public void after() {
        log.info("发放后执行后...");
    }
}
