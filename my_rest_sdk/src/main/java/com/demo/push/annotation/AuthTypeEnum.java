package com.demo.push.annotation;

/**
 * ClassName:AuthTypeEnum
 * Package:com.demo.push.annotation
 * Description: 鉴权类型
 *
 * @Date:2023/4/7 11:25
 * @Author:qs@1.com
 */
public enum AuthTypeEnum {
    TOKEN(AuthTypeDefinition.API_AUTH_TYPE_TOKEN),
    SIGN(AuthTypeDefinition.API_AUTH_TYPE_SIGN),
    NONE(AuthTypeDefinition.API_AUTH_TYPE_NONE);

    private final int value;

    AuthTypeEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
