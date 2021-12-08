package com.example.boot.response;

import com.example.boot.enums.ResultCodeEnum;

/**
 * ClassName:Result
 * Package:com.example.boot.response
 * Description:
 *
 * @Date:2021/8/13 16:38
 * @Author:qs@1.com
 */
public class Result {
    private Integer status; 	// 响应状态
    private String msg;		// 响应消息
    private Object data;	// 响应数据

    // OK状态信息返回
    public static Result OK(){
        return new Result(null);
    }
    public static Result OK(Object data){
        return new Result(data);
    }

    public Result(Object data) {
        this.status = ResultCodeEnum.OK.getStatus();
        this.msg = ResultCodeEnum.OK.getMsg();
        this.data = data;
    }

    // fail状态返回信息
    public static Result fail(Integer status, String msg){
        return build(status, msg, null);
    }
    public static Result fail(Integer status, String msg, Object data){
        return build(status, msg, data);
    }


    // 自定义响应参数
    public static Result build(Integer status, String msg, Object data){
        return new Result(status, msg, data);
    }
    public static Result build(Integer status, String msg) {
        return new Result(status, msg, null);
    }

    public Result(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }



    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public Object getData() {
        return data;
    }
    public void setData(Object data) {
        this.data = data;
    }

}
