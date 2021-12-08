package com.example.boot.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * ClassName:Phone
 * Package:com.example.boot.annotation
 * Description:
 *
 * @Date:2021/11/25 13:35
 * @Author:qs@1.com
 */
@Documented
@Constraint(validatedBy = {IdCardValidator.class}) // 指定注解的实现类
@Retention(RUNTIME)
@Target({METHOD, FIELD})
public @interface IdCard {
    String message() default "请输入正确的身份证号";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    @Target({ METHOD, FIELD })
    @Retention(RUNTIME)
    @Documented
    public @interface List {
        IdCard[] value();
    }
}
