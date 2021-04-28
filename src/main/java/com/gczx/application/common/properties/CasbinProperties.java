package com.gczx.application.common.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author : leifeijin
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
