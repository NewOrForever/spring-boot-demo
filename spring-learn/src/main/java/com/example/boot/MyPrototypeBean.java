package com.example.boot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ClassName:MyPrototypeBean
 * Package:com.example.boot
 * Description:
 *
 * @Date:2021/12/8 15:12
 * @Author:qs@1.com
 */
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Bean
@Scope("prototype")
public @interface MyPrototypeBean {
}
