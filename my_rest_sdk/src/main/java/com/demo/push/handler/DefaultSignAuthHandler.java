package com.demo.push.handler;

import com.demo.push.ApiConfiguration;
import com.demo.push.core.ApiProxyFactory;

import java.util.Map;

/**
 * ClassName:DefaultSignAuthHandler
 * Package:com.demo.push.handler
 * Description: sign 加解签
 *
 * @Date:2023/4/7 17:18
 * @Author:qs@1.com
 */
public class DefaultSignAuthHandler implements AuthHandler {
    @Override
    public void handle(ApiConfiguration configuration, Map<String, Object> header, ApiProxyFactory.ApiParam apiParam) {
    }
}
