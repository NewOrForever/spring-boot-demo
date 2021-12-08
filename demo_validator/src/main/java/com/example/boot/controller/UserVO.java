package com.example.boot.controller;

import com.example.boot.annotation.IdCard;
import com.example.boot.annotation.Phone;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

/**
 * ClassName:UserVO
 * Package:com.example.boot.controller
 * Description:
 *
 * @Date:2021/11/24 15:47
 * @Author:qs@1.com
 */

@Builder
@ApiModel(description = "用户对象")
@Data
public class UserVO {
    @ApiModelProperty(value = "用户id", example = "0")
    private Integer id;

    @ApiModelProperty("用户名称")
    @NotEmpty(message = "用户名不能为空")
    @Length(min = 6, max = 12, message = "用户名长度必须位于6~12位")
    private String username;

    @NotEmpty(message = "密码不能为空")
    @ApiModelProperty("密码")
    @Length(min = 6, message = "密码长度不能小于6")
    private String password;

    @Email(message = "请输入正确的邮箱")
    @ApiModelProperty("邮箱")
    private String email;

    @Phone
    @ApiModelProperty("手机号")
    private String phone;

    //@Pattern(regexp = "^(\\d{18}|\\d{15}|(\\d{17}[x|X]))$", message = "身份证格式错误")
    @IdCard
    @ApiModelProperty("身份证")
    private String idCard;

    @ApiModelProperty("性别")
    private Byte sex;

    @ApiModelProperty(value = "删除状态")
    private Byte deleted;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("创建时间")
    private Date createTime;


}
