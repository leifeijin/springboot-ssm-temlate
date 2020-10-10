package com.gczx.application.common.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author: leifeijin
 * @Date: 2020/9/21
 * @Description:
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "casbin")
public class CasbinProperties {
    private String driver;
    private String url;
    private String username;
    private String password;
    private String model;
}
