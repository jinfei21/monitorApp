package com.ppdai.monitor.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author haijiang
 * @Description Swagger2Config
 * @Date 2018/09/18
 **/
@Profile({"dev", "test"})
@Configuration
@EnableSwagger2
public class Swagger2Config {

    /**
     * 扫描基类包
     */
    private static final String SWAGGER_SCAN_BASE_PACKAGE = "com.ppdai.monitor.controller";

    /**
     * 接口版本
     */
    public static final String VERSION = "2.0.0";

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(SWAGGER_SCAN_BASE_PACKAGE))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("监控接口文档")
                .description("监控服务")
                .version(VERSION)
                .build();
    }

}
