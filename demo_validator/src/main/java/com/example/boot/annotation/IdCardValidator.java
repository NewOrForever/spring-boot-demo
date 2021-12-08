package com.example.boot.annotation;

import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * ClassName:PhoneValidator
 * Package:com.example.boot.annotation
 * Description: 验证手机号
 *
 * @Date:2021/11/25 13:46
 * @Author:qs@1.com
 */
public class IdCardValidator implements ConstraintValidator<IdCard, String> {
    @Override
    public void initialize(IdCard constraintAnnotation) {

    }

    /**
     * 校验的实现逻辑
     * @param value
     * @param context
     * @return
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (!StringUtils.hasLength(value)){
            return false;
        }
        return CheckIdCard.check(value);
    }

}
