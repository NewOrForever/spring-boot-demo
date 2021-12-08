package com.example.boot.config;

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

/**
 * ClassName:SwaggerConfiguration
 * Package:com.example.boot.config
 * Description:
 *
 * @Date:2021/8/11 15:12
 * @Author:qs@1.com
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Value("${SPRING.SWAGGER2.ENABLED}")
    private boolean swaggerEnabled;

    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .enable(swaggerEnabled)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.boot"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("接口文档")
                .description("springboot整合swagger")
                .termsOfServiceUrl("127.0.0.1:9090/test/user/update")
                .version("1.0")
                .build();
    }

}
