package com.example.boot.controller;

import com.example.boot.response.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;


/**
 * ClassName:UserController
 * Package:com.example.boot.controller
 * Description:
 *
 * @Date:2021/8/11 17:05
 * @Author:qs@1.com
 */
@Api(tags = "用户接口")
@RestController
@RequestMapping("/user")
public class UserController {

    @ApiOperation("根据id修改用户数据")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "用户主键", required = true, paramType = "path")
    })
    @GetMapping(value = "/u/{id}")
    public UserVo findById(@PathVariable Long id){
        UserVo userVo = new UserVo();
        userVo.setId(id);
        userVo.setName("swagger");
        userVo.setPassword("123456");
        return userVo;
    }

    @ApiOperation("添加用户")
    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public UserVo createUser(@RequestBody UserVo userVo){
        return userVo;
    }


    /**
     * 封装了返回的类Result
     * @return
     */
    @GetMapping("/resp/getModel")
    public Result getUserVo(){
        UserVo userVo = new UserVo();
        userVo.setId(1L);
        userVo.setName("swagger");
        userVo.setPassword("123456");
        return Result.OK(userVo);
    }

    /*************** @ControllerAdvice ****************/
    @GetMapping("/getString")
    public String getString(){
        return "test";
    }
    @GetMapping("/empty")
    public void empty(){

    }
    @GetMapping("/getObject")
    public List<UserVo> getObject(){
        List<UserVo> userVoList = new LinkedList<>();
        UserVo userVo = new UserVo();
        userVo.setId(1L);
        userVo.setName("swagger");
        userVo.setPassword("123456");
        userVoList.add(userVo);

        return userVoList;
    }




}
