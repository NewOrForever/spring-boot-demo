package com.learn.generics;

import java.lang.reflect.Field;
import java.lang.reflect.TypeVariable;

/**
 * ClassName:TypeTest
 * Package:com.learn.generics
 * Description:
 *
 * @Date:2022/1/12 16:27
 * @Author:qs@1.com
 */
public class GenericsTypeTest {
    public static void main(String[] args) {
        User user = new User();
        TypeVariable<? extends Class<?>>[] typeParameters = user.getClass().getSuperclass().getTypeParameters();
        for (TypeVariable<? extends Class<?>> typeParameter : typeParameters) {
            System.out.println(typeParameter.getName()); // O  S
        }

        // com.learn.generics.BaseService
        System.out.println(user.getClass().getSuperclass().getTypeName());

        // com.learn.generics.BaseService<com.learn.generics.Order, com.learn.generics.Stock>
        System.out.println(user.getClass().getGenericSuperclass().getTypeName());

        Field[] fields = user.getClass().getSuperclass().getDeclaredFields();
        for (Field field : fields) {
            System.out.println(field.getName());
            System.out.println(field.getGenericType());
        }

    }
}
