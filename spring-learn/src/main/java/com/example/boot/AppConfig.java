package com.example.boot;

import com.example.boot.service.OrderService;
import com.example.boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.context.annotation.*;

/**
 * ClassName:AppConfig
 * Package:com.example.boot
 * Description:
 *
 * @Date:2021/12/8 13:31
 * @Author:qs@1.com
 */
@Configuration
//@ComponentScan(basePackages = "com.example.boot")
@PropertySource("classpath:spring.properties")
public class AppConfig {

    @Bean
    public UserService userService() {
        return  new UserService();
    }

    @Bean
    public OrderService orderService() {
        System.out.println(userService());
        System.out.println(userService());
        return  new OrderService();
    }

}
