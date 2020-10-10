package com.gczx.application.common.config;

import com.gczx.application.common.properties.CasbinProperties;
import org.casbin.jcasbin.main.Enforcer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import java.io.File;

/**
 * @Author: leifeijin
 * @Date: 2020/9/21
 * @Description: casbin配置类
 */
@Configuration
@EnableConfigurationProperties(CasbinProperties.class)
public class CasbinConfig {
    private final CasbinProperties casbinProperties;

    public CasbinConfig(CasbinProperties casbinProperties) {
        this.casbinProperties = casbinProperties;
    }

    @Bean
    Enforcer getEnforcer() throws Exception {
        MyJdbcAdapter dmJdbcAdapter = new MyJdbcAdapter(casbinProperties.getDriver(), casbinProperties.getUrl(), casbinProperties.getUsername(), casbinProperties.getPassword());
        File file = ResourceUtils.getFile(casbinProperties.getModel());
        Enforcer enforcer = new Enforcer(file.getPath(), dmJdbcAdapter);
        enforcer.loadPolicy();
        return enforcer;
    }
}
