package com.example.boot.imports;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * ClassName:Main
 * Package:com.example.boot.imports
 * Description:
 *
 * @Date:2021/11/29 15:42
 * @Author:qs@1.com
 */
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(UserService.class);
        UserService userService = context.getBean(UserService.class);
        UserBean userBean = context.getBean(UserBean.class);
        System.out.println(userService);
        System.out.println(userBean);
    }
}
