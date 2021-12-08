package com.example.boot.exceptions;

import com.example.boot.enums.ResultCodeEnum;
import lombok.Data;

/**
 * ClassName:BussinessException
 * Package:com.example.boot.exceptions
 * Description:
 *
 * @Date:2021/8/17 13:15
 * @Author:qs@1.com
 */
@Data
public class CustomException extends RuntimeException {

    protected Integer code;
    protected String message;

    public CustomException(ResultCodeEnum resultCode) {
        this.code = resultCode.getStatus();
        this.message = resultCode.getMsg();
    }

}
