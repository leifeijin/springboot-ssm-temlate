package com.gczx.application.common.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 数据库数据源
 * @author leifeijin
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "spring.datasource")
public class DatabaseProperties {
    /**
     * 数据库类型
     */
    private String dbType;
    /**
     * 数据库url
     */
    private String url;
    /**
     * 数据库连接用户名
     */
    private String username;
    /**
     * 数据库连接密码
     */
    private String password;
    /**
     * 数据库驱动名
     */
    private String driverClassName;
}
