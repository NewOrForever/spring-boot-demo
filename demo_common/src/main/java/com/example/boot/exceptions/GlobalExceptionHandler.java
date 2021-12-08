package com.example.boot.exceptions;

import com.example.boot.enums.ResultCodeEnum;
import com.example.boot.response.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * ClassName:GlobalExceptionHandler
 * Package:com.example.boot.exceptions
 * Description:
 *
 * @Date:2021/8/16 17:19
 * @Author:qs@1.com
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     * 处理全局异常
     * @param e
     * @param request
     * @return
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    public ErrorResult handleThrowable(Throwable e, HttpServletRequest request){
        // TODO 运行时异常，可以在这里记录，用于发异常邮件通知
        ErrorResult errorResult = ErrorResult.fail(ResultCodeEnum.SYSTEM_ERROR, e);
        log.error("URL:{}, 系统异常", request.getRequestURI(), e );
        return errorResult;
    }

    /**
     *  自定义异常处理器
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(CustomException.class)
    public ErrorResult handleCustomException(CustomException e, HttpServletRequest request){
        ErrorResult errorResult = ErrorResult.builder().status(e.code)
                .message(e.message)
                .exception(e.getClass().getName())
                .build();
        // 已知异常使用warnning
        log.warn("URL: {}, 业务异常{}", request.getRequestURL(), errorResult);
        return errorResult;
    }

    /**
     * 处理参数校验的异常
     * @param e
     * @param request
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResult handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request){
        String msgs = this.handle(e.getBindingResult().getFieldErrors());
        ErrorResult errorResult = ErrorResult.fail(ResultCodeEnum.PARAMETER_IS_INVALID, e, msgs);
        // 已知异常使用warnning
        log.warn("URL：{}，参数校验异常：{}", request.getRequestURI(), msgs);
        return errorResult;
    }
    private String handle(List<FieldError> fieldErrors) {
        StringBuilder sb = new StringBuilder();
        for (FieldError error : fieldErrors) {
            sb.append(error.getField()).append("=[").append(error.getDefaultMessage()).append("] ");
        }
        return sb.toString();
    }

    /**
     *  Assert校验的异常
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult handleIllegalArgumentException(IllegalArgumentException e, HttpServletRequest request) {
        ErrorResult errorResult = ErrorResult.builder()
                .status(ResultCodeEnum.ASSERT_FAIL.getStatus())
                .exception(e.getClass().getName())
                .message(e.getMessage())
                .build();

        log.warn("URL：{}，断言异常：{}", request.getRequestURI(), e.getMessage());
        return errorResult;
    }




}
