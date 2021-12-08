package com.example.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import sq.core.factories.UserModel;

@SpringBootApplication
public class DemoIocFactoriesApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(DemoIocFactoriesApplication.class, args);
        UserModel userModel = (UserModel) run.getBean("userModel");
        System.out.println(userModel.info());
    }

}
