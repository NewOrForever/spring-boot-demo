package com.example.boot;

import org.springframework.beans.factory.annotation.Value;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ClassName:MyValue
 * Package:com.example.boot
 * Description:
 *
 * @Date:2021/12/8 14:14
 * @Author:qs@1.com
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Value("${VALUE_TEST}")
public @interface MyValue {
}
