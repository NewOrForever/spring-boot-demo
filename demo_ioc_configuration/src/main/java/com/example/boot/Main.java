package com.example.boot;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * ClassName:Main
 * Package:com.example.boot
 * Description:
 *
 * @Date:2021/11/26 16:22
 * @Author:qs@1.com
 */

public class Main
{
    public static void main(String[] args) {
        // 初始化指定的容器
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyConfiguration.class);
        UserBean userBean = (UserBean) context.getBean("userBean");
        System.out.println(userBean);
    }
}
