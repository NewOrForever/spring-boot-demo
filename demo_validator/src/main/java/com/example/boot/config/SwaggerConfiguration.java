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
 * @Date:2021/11/24 14:42
 * @Author:qs@1.com
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Value("${SPRING.SWAGGER2.ENABLED}")
    private boolean SPRING_SWAGGER_ENABLED;

    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .enable(SPRING_SWAGGER_ENABLED)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.boot"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("接口文档")
                .description("springboot的validator校验")
                .termsOfServiceUrl("https://www.1juhao.com")
                .version("1.0")
                .build();

    }

}
