package com.example.boot.selector;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * ClassName:EnableUserConfiguration
 * Package:com.example.boot.selector
 * Description:
 *
 * @Date:2021/11/29 17:05
 * @Author:qs@1.com
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(UserImportSelector.class)
public @interface EnableUserConfiguration {
}
