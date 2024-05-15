package com.example.boot;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
@Slf4j
class MinioApplicationTests {

    @Test
    void contextLoads() {

    }

    @Test
    public void lombokLogTest() {
        System.out.println("Hello World");
    }
}
