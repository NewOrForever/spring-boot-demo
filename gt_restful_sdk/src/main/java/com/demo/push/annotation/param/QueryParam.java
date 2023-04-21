package com.demo.push.annotation.param;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ClassName:QueryParam
 * Package:com.demo.push.annotation.param
 * Description: 修饰HTTP请求中的query参数 ---> 同 @RequestParameter
 *
 * @Date:2023/3/2 15:12
 * @Author:qs@1.com
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface QueryParam {
    String name();
}

