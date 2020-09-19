package com.gczx.application.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Value("${swagger.controller}")
    private String swaggerController;
    @Value("${swagger.title}")
    private String swaggerTitle;
    @Value("${swagger.description}")
    private String swaggerDescription;
    @Value("${swagger.version}")
    private String swaggerVersion;

    /**
     * 自定义swagger扫描的controller路径
     * 如果某个接口不想暴露,可以在controller上使用@ApiIgnore,该接口就不会暴露在swagger2页面下
     * @return docket类
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(swaggerController))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 创建一个ApiInfo实例,swagger页面展示的文档信息内容
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(swaggerTitle)
                .description(swaggerDescription)
                .version(swaggerVersion)
                .build();
    }
}
