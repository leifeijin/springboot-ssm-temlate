package com.gczx.application.common.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: leifeijin
 * @Date: 2020/9/15
 * @Description: mybatis plus配置文件
 */
@Configuration
@MapperScan("com.gczx.application.service.dao*")
public class MyBatiesPlusConfig {
}
