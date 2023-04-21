package com.demo.push.annotation.method;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ClassName:BodyParam
 * Package:com.demo.push.annotation.param
 * Description: HTTP 请求 body 参数
 *
 * @Date:2023/3/2 15:15
 * @Author:qs@1.com
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MyPost {
    /**
     * 接口相对路径, 不包含路径参数
     * eg. /auth
     *
     * @return
     */
    String uri();

    /**
     * 是否需要token，默认需要
     *
     * @return
     */
    boolean needToken() default true;

    boolean isAuth() default false;
}
