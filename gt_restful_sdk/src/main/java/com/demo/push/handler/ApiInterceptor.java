package com.demo.push.handler;

import com.demo.push.common.ApiException;
import com.demo.push.core.ApiProxyFactory;

import java.util.Map;

/**
 * ClassName:ApiInterceptor
 * Package:com.demo.push.handler
 * Description:
 *
 * @Date:2023/4/3 17:50
 * @Author:qs@1.com
 */
public interface ApiInterceptor {
    /**
     * http请求前调用
     *
     * @param apiParam 请求参数
     * @param header   请求header
     * @param body     请求body
     */
    void pre(ApiProxyFactory.ApiParam apiParam, Map<String, Object> header, String body);

    /**
     * http请求成功后调用此方法
     *
     * @param apiParam 请求参数
     * @param header   请求header
     * @param body     请求body
     * @param result   返回值
     */
    void post(ApiProxyFactory.ApiParam apiParam, Map<String, Object> header, String body, String result);

    /**
     * 报错时调用此方法
     *
     * @param apiParam 请求参数
     * @param header   请求header
     * @param body     请求body
     * @param e        异常信息
     */
    void handleException(ApiProxyFactory.ApiParam apiParam, Map<String, Object> header, String body, ApiException e);

    /**
     * http请求后调用，不管成功或者失败都会调用
     *
     * @param apiParam 请求参数
     * @param header   请求header
     * @param body     请求body
     * @param result   返回值
     */
    void afterCompletion(ApiProxyFactory.ApiParam apiParam, Map<String, Object> header, String body, String result);
}
