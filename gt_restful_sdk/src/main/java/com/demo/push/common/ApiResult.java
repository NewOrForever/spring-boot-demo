package com.demo.push.common;

/**
 * ClassName:ApiResult
 * Package:com.demo.push.common
 * Description:
 *
 * @Date:2023/4/3 17:06
 * @Author:qs@1.com
 */
public class ApiResult<T> {
    protected int code;
    protected String msg;
    protected T data;

    public ApiResult() {
    }

    public ApiResult(String message, int code) {
        this.msg = message;
        this.code = code;
    }

    public static <T> ApiResult<T> success(T data) {
        return new ApiResult<T>(data);
    }

    public static ApiResult success() {
        ApiResult apiResult = new ApiResult();
        apiResult.code = 200;
        return apiResult;
    }

    public ApiResult(T data) {
        this.code = 200;
        this.data = data;
    }

    public static ApiResult fail(String msg, int code) {
        return new ApiResult(msg, code);
    }

    public static ApiResult fail(String msg) {
        return new ApiResult(msg, 500);
    }

    public boolean isSuccess() {
        return code == 0;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ApiResult{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
