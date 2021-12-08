package com.example.boot.annotation;

import com.example.boot.controller.UserVO;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ClassName:PhoneValidator
 * Package:com.example.boot.annotation
 * Description: 验证手机号
 *
 * @Date:2021/11/25 13:46
 * @Author:qs@1.com
 */
public class PhoneValidator implements ConstraintValidator<Phone, String> {

    private static final Pattern PHONE_PATTERN = Pattern.compile(
            "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$"
    );

    @Override
    public void initialize(Phone constraintAnnotation) {

    }

    /**
     * 校验的实现逻辑
     *
     * @param value
     * @param context
     * @return
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        Class<UserVO> userVOClass = UserVO.class;
//        if (userVOClass.isAnnotationPresent(Phone.class)) {
//            Phone annotation = userVOClass.getAnnotation(Phone.class);
//        }

//        try {
//            Field field = userVOClass.getDeclaredField("phone");
//            if (field.isAnnotationPresent(Phone.class)) {
//                Phone fieldAnnotation = field.getAnnotation(Phone.class);
//                if (fieldAnnotation != null) {
//                    field.setAccessible(true);
//                    field.set("phone", fieldAnnotation.message());
//                }
//            }
//
//        } catch (NoSuchFieldException | IllegalAccessException e) {
//            e.printStackTrace();
//        }


        if (!StringUtils.hasLength(value)) {
            return false;
        }
        Matcher matcher = PHONE_PATTERN.matcher(value);
        return matcher.matches();
    }

}
