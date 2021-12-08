package com.example.boot.controller;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * ClassName:UserVo
 * Package:com.example.boot.controller
 * Description:
 *
 * @Date:2021/8/10 14:04
 * @Author:qs@1.com
 */
@Data // lombok注解
@ApiModel(description = "用户对象")
public class UserVo {
    @ApiModelProperty(value = "用户id", example = "0")
    private Long id;
    @ApiModelProperty("用户昵称")
    private String name;
    @ApiModelProperty("用户密码")
    private String password;
}
