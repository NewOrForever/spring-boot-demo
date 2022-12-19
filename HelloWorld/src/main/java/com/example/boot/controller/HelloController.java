package com.example.boot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * ClassName:HelloController
 * Package:com.example.boot.controller
 * Description:
 *
 * @Date:2021/8/9 19:55
 * @Author:qs@1.com
 */
@RestController
@Slf4j
public class HelloController {
    // crtl + alt + v
    // 被@Slf4j代替
//    public static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    @Value("${HELLO.MSG}")
    public String Hello_Msg;

    @RequestMapping("/hello")
    public String hello() {
        System.out.println("===========>wellcome to docker ...");
        return "hello world";
    }

    @GetMapping("/msg")
    public String getMsg() {
        return Hello_Msg;
    }

    @GetMapping("/log")
    public void getLog() {
        log.trace("trace");
        log.trace("debug");
        log.info("info");
        log.warn("warn");
        log.error("error");
    }

    @GetMapping("/user")
    public UserVo getUser() {
        UserVo userVo = new UserVo();
        userVo.setId(1L);
        userVo.setName("lombok");
        userVo.setPassword("123");

        return userVo;
    }


}
