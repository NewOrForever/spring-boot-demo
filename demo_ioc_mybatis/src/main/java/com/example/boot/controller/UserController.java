package com.example.boot.controller;

import com.example.boot.entity.User;
import com.example.boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

/**
 * ClassName:UserController
 * Package:com.example.boot.controller
 * Description:
 *
 * @Date:2021/12/1 16:44
 * @Author:qs@1.com
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 初始化创建1000条数据
     */
    @GetMapping("/init")
    public void init(){
        for (int i = 0; i < 1000; i++) {
            User user = new User();
            String temp = "user" + i;
            user.setUsername(temp);
            user.setPassword(temp);
            Random random = new Random();
            user.setSex((byte)random.nextInt(2));

            userService.createUser(user);
        }
    }

    /**
     * 更相信某条数据
     * @param id
     */
    @GetMapping("/update/{id}")
    public void update(@PathVariable Integer id) {
        User user = new User();
        user.setId(id);
        String temp = "update" + id;
        user.setUsername(temp);
        user.setPassword(temp);
        userService.updateUser(user);
    }

    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public void find() {
        userService.findExample();
    }
}
