package com.example.boot.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * ClassName:UserController
 * Package:com.example.boot.controller
 * Description:
 *
 * @Date:2021/11/24 15:12
 * @Author:qs@1.com
 */
@Api(tags = "用户接口")
@RestController
@RequestMapping("/user")
public class UserController {

    @ApiOperation("根据id获取用户")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "id", value = "用户id", required = true, paramType = "path")
    )
    @GetMapping("/findUser/{id}")
    public UserVO findByUserId(@PathVariable Integer id) {
        UserVO user = UserVO.builder()
                .id(id)
                .username("张三")
                .password("123456")
                .email("12.qq")
                .phone("132456789")
                .idCard("32598878966635")
                .build();

        return user;
    }

    @ApiOperation("创建用户")
    @PostMapping(value = "/create", produces = APPLICATION_JSON_UTF8_VALUE, consumes = APPLICATION_JSON_UTF8_VALUE)
    public void createUser(@RequestBody @Validated UserVO userVO){

    }

    @ApiOperation("更新用户")
    @PostMapping(value = "/update", produces = APPLICATION_JSON_UTF8_VALUE, consumes = APPLICATION_JSON_UTF8_VALUE)
    public void updateUser(@RequestBody @Validated UserVO userVO) {
//        UserVO user = null;
//        Assert.notNull(user, "用户不存在");

        UserVO user = userVO;
        UserVO user2 = user;
        Assert.isTrue(user == user2, "用户不匹配");

        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        Assert.notEmpty(map, "map为空");
    }

}
