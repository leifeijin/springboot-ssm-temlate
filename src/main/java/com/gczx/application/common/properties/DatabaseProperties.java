package com.gczx.application.common.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: leifeijin
 * @Date: 2020/9/25
 * @Description:
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "spring.datasource")
public class DatabaseProperties {
    private String url;
    private String username;
    private String password;
}
