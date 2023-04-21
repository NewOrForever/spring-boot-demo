package com.demo.push.spring;

import com.demo.push.ApiHelper;
import com.demo.push.ApiConfiguration;
import com.demo.push.common.ApiException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * ClassName:ApiBeanFactory
 * Package:com.demo.push.spring
 * Description: 整合spring，扫描器启动的时候就缓存起来同mybatis 一样
 * @see com.demo.push.annotation.RestApi
 *
 * @Date:2023/4/6 16:49
 * @Author:qs@1.com
 */
public class ApiBeanFactory<T> implements FactoryBean<T>, InitializingBean {
    /**
     * 创建代理类的工厂类
     */
    private ApiHelper apiHelper;

    private Class<T> apiClass;

    private T api;

    /**
     * 标记是否调用方法 {@link #afterPropertiesSet()}
     */
    volatile boolean calledMethodAfterPropertiesSet = false;

    public ApiBeanFactory(ApiConfiguration configuration) {
        if (configuration == null) {
            throw new ApiException("configuration cannot be null.", true);
        }
        apiHelper = ApiHelper.build(configuration);
    }

    @Override
    public T getObject() {
        if (api != null) {
            return api;
        }
        if (!calledMethodAfterPropertiesSet) {
            throw new ApiException("please call method afterPropertiesSet first.");
        } else {
            // 理论上是不会出现这种可能性的
            throw new ApiException("api is null, please check.");
        }
    }

    @Override
    public Class<?> getObjectType() {
        return apiClass;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void afterPropertiesSet() {
        if (apiHelper == null) {
            throw new ApiException("gtApiProxyFactory cannot be null.", true);
        }
        if (apiClass == null) {
            throw new ApiException("apiClass cannot be null.", true);
        }
        api = apiHelper.creatApi(apiClass);
        this.calledMethodAfterPropertiesSet = true;
    }

    public void setApiClass(Class<T> apiClass) {
        this.apiClass = apiClass;
    }
}
