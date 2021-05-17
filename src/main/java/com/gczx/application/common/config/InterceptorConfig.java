package com.gczx.application.common.config;

import com.gczx.application.common.interceptor.AuthenticationInterceptor;
import com.gczx.application.common.interceptor.CasbinInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.stream.Collectors;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Value("${interceptor.excludePathPatterns}")
    private String excludePathPatterns;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor())
                .addPathPatterns("/api/**")
                // 使用java8 stream().map().collect()用法去掉每个url的空格
                .excludePathPatterns(Arrays.stream(excludePathPatterns.split(","))
                        .map(String::trim).collect(Collectors.toList()));
        registry.addInterceptor(casbinInterceptor())
                .addPathPatterns("/api/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

    }

    @Bean
    public AuthenticationInterceptor authenticationInterceptor() {
        return new AuthenticationInterceptor();
    }

    @Bean
    public CasbinInterceptor casbinInterceptor() {
        return new CasbinInterceptor();
    }
}
