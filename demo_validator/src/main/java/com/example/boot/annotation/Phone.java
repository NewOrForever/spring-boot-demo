package com.example.boot.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static  java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * ClassName:Phone
 * Package:com.example.boot.annotation
 * Description:
 *
 * @Date:2021/11/25 13:35
 * @Author:qs@1.com
 */
@Documented                                                          // 文档标记
@Constraint(validatedBy = {PhoneValidator.class}) // 指定注解的实现类
@Retention(RUNTIME)                                             // 生命周期，存在于class字节码文件中，运行时可通过反射获取到
@Target({METHOD, FIELD})                                      // 作用域
public @interface Phone {
    String message() default "请输入正确的手机号码";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    @Target({ METHOD, FIELD })
    @Retention(RUNTIME)
    @Documented
    public @interface List {
        Phone[] value();
    }
}
