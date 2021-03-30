package com.gczx.application.common.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * AOP增强类
 * @author leifeijin
 */
@Slf4j
@Aspect
@Component
public class DemoProxy {
    /**
     * 定义切入点
     */
    @Pointcut(value = "execution(* com.gczx.application.controller.DemoController.getDemo(..))")
    public void pointCut() {

    }

    @Before(value = "pointCut()")
    public void before() {
        log.info("getDemo执行前...");
    }

    @After(value = "pointCut()")
    public void after() {
        log.info("getDemo执行后...");
    }
}
