package com.example.boot;

import com.example.boot.controller.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
@Slf4j
class HelloWorldApplicationTests {

    @Test
    void contextLoads() {

    }

    @Test
    public void lombokLogTest() {
        UserVo userVo = new UserVo();
        userVo.setName("test");
        log.info("======================{}", userVo.getName());
        String exp = "test";
        Assertions.assertEquals(exp, userVo.getName(), "fail test");
    }
}
