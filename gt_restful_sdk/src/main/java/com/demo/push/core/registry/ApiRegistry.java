package com.demo.push.core.registry;

import com.demo.push.core.ApiProxyFactory;

import java.lang.reflect.Method;

/**
 * ClassName:ApiRegistry
 * Package:com.demo.push.core
 * Description:
 *
 * @Date:2023/4/3 16:21
 * @Author:qs@1.com
 */
public interface ApiRegistry {
    /**
     * 注册, 解析出HTTP请求方法、路径、返回值类型等并缓存
     *
     * @param method
     */
    void register(Method method);

    /**
     * 获取缓存的方法信息，如果没有缓存则解析并缓存
     *
     * @param method 方法
     * @return
     */
    ApiProxyFactory.BaseParam get(Method method);
}
