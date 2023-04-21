package com.demo.push.handler;

import com.demo.push.ApiConfiguration;
import com.demo.push.core.ApiProxyFactory;

import java.util.Map;

/**
 * ClassName:AuthHandler
 * Package:com.demo.push.handler
 * Description: api 调用的授权处理
 *
 * @Date:2023/4/7 14:34
 * @Author:qs@1.com
 */
public interface AuthHandler {

    void handle(ApiConfiguration configuration, Map<String, Object> header, ApiProxyFactory.ApiParam apiParam);
}
