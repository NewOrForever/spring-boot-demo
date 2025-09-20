package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName:CacheTestController
 * Package:springboot
 * Description:
 *
 * @Date:2025/9/15 17:27
 * @Author:qs@1.com
 */
@RestController
@RequestMapping("/jetcache")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("/getUser")
    public User getUser(Long id) {
        return userService.getUser(id);
    }

    @GetMapping("/updateUser")
    public void updateUser(User user) {
        userService.update(user);
    }

    @GetMapping("/deleteUser")
    public void deleteUser(Long id) {
        userService.delete(id);
    }
}
