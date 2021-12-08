package com.example.boot.controller;

import com.example.boot.enums.ResultCodeEnum;
import com.example.boot.exceptions.CustomException;
import com.example.boot.service.AppPushService;
import com.example.boot.service.RedisService;
import com.example.boot.service.ScoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * ClassName:TestController
 * Package:com.example.boot.controller
 * Description:
 *
 * @Date:2021/8/11 9:22
 * @Author:qs@1.com
 */
@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private AppPushService appPushService;

    @RequestMapping(method = RequestMethod.POST, value = "/async")
    public String testAsync(){
        log.info("-------------注册用户------------");
         // 该方法异步处理，另起了个线程
        scoreService.addScore();
        return "ok";
    }

    @RequestMapping("/async2")
    public String testAsyncPool(){
        log.info("-------------注册用户------------");
        scoreService.addScore2();
        return "ok";
    }

    @RequestMapping("/user/update")
    public String updateUser(){
        log.info("-------------更改用户信息------------");
        redisService.refreshRedis();
        return "ok";
    }

    @RequestMapping("/order/create")
    public String createOrder(){
        log.info("-------------下单------------");
        appPushService.appPush();
        return "ok";
    }

    @PostMapping("/error1")
    public void error1(){
        Integer a = Integer.valueOf("a");
    }

    @GetMapping("/error2")
    public void error2(){
        throw new RuntimeException("");
    }

    @GetMapping("/error3")
    public void error3(){
        throw new CustomException(ResultCodeEnum.USER_EXISTED);
    }

    // 空指针异常模拟
    @GetMapping("/null")
    public void errorNull(){
        UserVo vo = null;
        vo.setId(1L);
    }
    // 自定义异常模拟登录失败
    @GetMapping("/login")
    public void login(){
        throw new CustomException(ResultCodeEnum.USER_LOGIN_FAIL);
    }


}
