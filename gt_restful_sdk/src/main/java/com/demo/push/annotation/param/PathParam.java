package com.demo.push.annotation.param;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ClassName:PathParam
 * Package:com.demo.push.annotation
 * Description: HTTP请求中的路径参数 -> 同 @PathVariable
 *
 * @Date:2023/3/2 15:11
 * @Author:qs@1.com
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface PathParam {
}
