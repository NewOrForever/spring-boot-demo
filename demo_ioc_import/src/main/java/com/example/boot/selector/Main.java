package com.example.boot.selector;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * ClassName:Main
 * Package:com.example.boot.selector
 * Description:
 *
 * @Date:2021/11/29 16:58
 * @Author:qs@1.com
 */

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(UserConfiguration.class);
        UserBean bean = context.getBean(UserBean.class);
        RoleBean roleBean = context.getBean(RoleBean.class);

        System.out.println(bean);
        System.out.println(roleBean);
    }
}
