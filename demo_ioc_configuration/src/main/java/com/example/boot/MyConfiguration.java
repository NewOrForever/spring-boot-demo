package com.example.boot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ClassName:MyConfiguration
 * Package:com.example.boot
 * Description: 使用@configuration注解，相当于sping中的<beans></beans>
 *
 * @Date:2021/11/26 16:26
 * @Author:qs@1.com
 */
@Configuration
public class MyConfiguration {
    public MyConfiguration() {
        System.out.println("MyConfiguration容器启动初始化。。。");
    }

    @Bean
    public UserBean userBean() {
        UserBean userBean = new UserBean();
        userBean.setUserName("库里");
        userBean.setPassword("123456");
        return userBean;
    }

}
