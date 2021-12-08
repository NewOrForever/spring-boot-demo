package com.example.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@MapperScan("com.example.boot.mapper")
@SpringBootApplication
public class DemoIocMybatisApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoIocMybatisApplication.class, args);
    }

}
