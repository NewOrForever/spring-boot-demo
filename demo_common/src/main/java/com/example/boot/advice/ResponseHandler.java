package com.example.boot.advice;

import com.example.boot.response.ErrorResult;
import com.example.boot.response.Result;
import com.example.boot.utils.JsonUtil;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * ClassName:ResponseHandler
 * Package:com.example.boot.advice
 * Description:
 *
 * @Date:2021/8/13 17:10
 * @Author:qs@1.com
 */
@ControllerAdvice(basePackages = "com.example.boot")
public class ResponseHandler implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    /**
     * 处理response的具体业务方法
     * @param o
     * @param methodParameter
     * @param mediaType
     * @param aClass
     * @param serverHttpRequest
     * @param serverHttpResponse
     * @return
     */
    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if (!(o instanceof ErrorResult)) {
            if (o instanceof String) {
                return JsonUtil.objectToJson(Result.OK(o));
            }
        } else {
            ErrorResult errorResult = (ErrorResult) o;
            return Result.fail(errorResult.getStatus(), errorResult.getMessage());
        }
        return Result.OK(o);
    }
}
