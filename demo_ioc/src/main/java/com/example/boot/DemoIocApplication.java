package com.example.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * springboot的核心是下面三个注解，注释掉springbootapplication后使用下面的注解也是能启动的
 */
//@SpringBootApplication
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan
public class DemoIocApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoIocApplication.class, args);
    }

}
