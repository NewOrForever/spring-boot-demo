package com.example.boot.response;

import com.example.boot.enums.ResultCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * ClassName:ErrorResult
 * Package:com.example.boot.response
 * Description:
 *
 * @Author:qs@1.com
 * @Date:2021/8/16 17:00
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ErrorResult {
    /**
     * 异常状态码
     */
    private Integer status;
    /**
     * 用户看得见的异常，例如：用户名重复
     */
    private String message;
    /**
     * 异常名称
     */
    private String exception;
    /**
     * 异常的堆栈信息
     */
    private String errors;

    /**
     * 对异常提示语进行封装
     * @param resultCode
     * @param e
     * @param message
     * @return
     */
    public static ErrorResult fail(ResultCodeEnum resultCode, Throwable e, String message){
        ErrorResult result = ErrorResult.fail(resultCode, e);
        result.setMessage(message);
        return result;
    }

    /**
     * 对异常枚举进行封装
     * @param resultCode
     * @param e
     * @return
     */
    public static ErrorResult fail(ResultCodeEnum resultCode, Throwable e){
        ErrorResult result = new ErrorResult();
        result.setStatus(resultCode.getStatus());
        result.setException(e.getClass().getName());
        result.setMessage(resultCode.getMsg());
//        result.setErrors(e.getStackTrace().toString());
        return result;
    }

}
