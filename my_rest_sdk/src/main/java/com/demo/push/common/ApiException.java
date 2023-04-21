package com.demo.push.common;

/**
 * ClassName:ApiException
 * Package:com.demo.push.common
 * Description:
 *
 * @Date:2023/3/2 14:06
 * @Author:qs@1.com
 */
public class ApiException extends RuntimeException {
    private int code = 500;

    private String message;

    public ApiException(String message) {
        super(message, null);
        this.message = message;
    }

    public ApiException(String message, boolean writableStackTrace) {
        super(message, null);
        this.message = message;
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }

    public ApiException(String message, int code) {
        super(message + "::" + code, null);
        this.code = code;
        this.message = message;
    }

    public ApiException(String message, int code, boolean writableStackTrace) {
        super(message + "::" + code, null);
        this.code = code;
        this.message = message;
    }

    public ApiException(String message, int code, Throwable throwable) {
        super(message + "::" + code, throwable);
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }
}
