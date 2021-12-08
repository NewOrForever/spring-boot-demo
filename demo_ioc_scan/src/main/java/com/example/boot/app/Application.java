package com.example.boot.app;

import com.example.boot.scan.TestComponentScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.example.boot.*") // 指定扫描bean的范围，如果这里不指定的话，默认是的扫描application所在包及子包
public class Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(Application.class, args);
        TestComponentScan bean = run.getBean(TestComponentScan.class);
        System.out.println(bean);
    }

}
