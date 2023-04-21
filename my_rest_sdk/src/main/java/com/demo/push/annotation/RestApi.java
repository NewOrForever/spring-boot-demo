package com.demo.push.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ClassName:RestApi
 * Package:com.demo.push.annotation
 * Description:
 *
 * @Date:2023/4/6 16:53
 * @Author:qs@1.com
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RestApi {
    /**
     * 自定义唯一属性，调用服务的id
     * @return
     */
    String serviceId() default "";

    AuthTypeEnum authType() default AuthTypeEnum.TOKEN;


}
