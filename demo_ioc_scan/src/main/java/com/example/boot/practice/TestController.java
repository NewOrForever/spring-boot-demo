package com.example.boot.practice;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName:TestController
 * Package:com.example.boot.practice
 * Description:
 *
 * @Date:2021/11/29 13:51
 * @Author:qs@1.com
 */
@RestController
public class TestController {
    @RequestMapping(value = "/test/hello", method = RequestMethod.GET)
    public String hello(){
        return "hello scan";
    }
}
