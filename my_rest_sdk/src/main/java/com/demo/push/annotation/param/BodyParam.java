package com.demo.push.annotation.param;

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
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface BodyParam {
}
