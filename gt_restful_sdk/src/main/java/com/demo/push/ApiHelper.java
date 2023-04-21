package com.demo.push;

import com.demo.push.api.AuthApi;
import com.demo.push.common.Assert;
import com.demo.push.core.ApiProxyFactory;
import com.demo.push.core.DefaultApiClient;
import com.demo.push.core.DefaultJson;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ClassName:ApiHelper
 * Package:com.demo.push
 * Description:
 *
 * @Date:2023/4/6 16:04
 * @Author:qs@1.com
 */
public class ApiHelper {
    private final ApiProxyFactory gtApiProxyFactory;

    private static final Object BUILD_LOCK = new Object();

    private static final Map<String, ApiHelper> apiHelperCache = new ConcurrentHashMap<String, ApiHelper>(4);

    /**
     * @param configuration 配置信息类
     * @return
     */
    public static ApiHelper build(MyApiConfiguration configuration) {
        return build(configuration, new DefaultJson());
    }

    /**
     * @param configuration 配置信息类
     * @return
     */
    public static ApiHelper build(MyApiConfiguration configuration, IJson json) {
        Assert.notNull(configuration, "configuration");
        configuration.check();
        String key = configuration.keyOfCache();
        ApiHelper apiHelper = apiHelperCache.get(key);
        if (apiHelper != null) {
            return apiHelper;
        }
        if (json == null) {
            json = new DefaultJson();
        }
        synchronized (BUILD_LOCK) {
            apiHelper = apiHelperCache.get(key);
            if (apiHelper != null) {
                return apiHelper;
            }
            final DefaultApiClient defaultApiClient = DefaultApiClient.build(configuration, json);
            ApiProxyFactory factory = ApiProxyFactory.build(defaultApiClient);
            final AuthApi authApi = factory.createProxy(AuthApi.class);
            defaultApiClient.setAuthApiAndAuth(authApi);
            apiHelper = new ApiHelper(factory);
            apiHelperCache.put(key, apiHelper);
            return apiHelper;
        }
    }

    /**
     * 删除缓存，并关闭守护线程，不影响已创建的Api的使用
     *
     * @param configuration
     */
    public static void close(MyApiConfiguration configuration) {
        Assert.notNull(configuration, "configuration");
        configuration.check();
        String key = configuration.keyOfCache();
        ApiHelper apiHelper = apiHelperCache.remove(key);
        if (apiHelper != null) {
            apiHelper.gtApiProxyFactory.close();
        }
    }

    private ApiHelper(ApiProxyFactory gtApiProxyFactory) {
        this.gtApiProxyFactory = gtApiProxyFactory;
    }

    /**
     * 创建接口实例
     *
     * @param apiClass {@link UserApi} {@link PushApi}
     * @param <T>
     * @return
     */
    public <T> T creatApi(Class<T> apiClass) {
        return this.gtApiProxyFactory.createProxy(apiClass);
    }

}
