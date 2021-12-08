package com.example.boot.enums;

/**
 * ClassName:ResultCodeEnum
 * Package:com.example.boot.enums
 * Description:
 *
 * @Date:2021/8/13 16:46
 * @Author:qs@1.com
 */
public enum ResultCodeEnum {

    OK(200, "OK"),

    SYSTEM_ERROR(10000, "系统异常，请稍后再试"),
    PARAMETER_IS_INVALID(10001, "参数验证失败！"),
    ASSERT_FAIL(10002, "Assert校验失败"),

    USER_EXISTED(20001, "   用户已存在"),
    USER_LOGIN_FAIL(20002, "登录失败！");

    private Integer status;
    private String msg;

    private ResultCodeEnum(int status, String msg) {
        this.setStatus(status);
        this.setMsg(msg);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
