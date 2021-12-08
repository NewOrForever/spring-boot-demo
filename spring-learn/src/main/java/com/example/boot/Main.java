package com.example.boot;

import com.example.boot.service.OrderService;
import com.example.boot.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * ClassName:Main
 * Package:com.example.boot
 * Description:
 *
 * @Date:2021/12/8 13:31
 * @Author:qs@1.com
 */
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
//        UserService userService = (UserService)context.getBean("userService");
        System.out.println(context.getBean("userService"));
        System.out.println(context.getBean("userService"));
        System.out.println(context.getBean("userService"));

//        System.out.println(context.getBean("appConfig"));
//        userService.test();
    }
}
