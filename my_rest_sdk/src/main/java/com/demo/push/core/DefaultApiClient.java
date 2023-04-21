package com.demo.push.core;

import com.demo.push.ApiConfiguration;
import com.demo.push.IJson;
import com.demo.push.api.AuthApi;
import com.demo.push.common.ApiException;
import com.demo.push.common.ApiResult;
import com.demo.push.common.Assert;
import com.demo.push.common.Config;
import com.demo.push.common.type.TypeReference;
import com.demo.push.dto.TokenDTO;
import com.demo.push.dto.req.AuthDTO;
import com.demo.push.handler.ApiInterceptor;
import com.demo.push.common.http.HttpManager;
import com.demo.push.handler.AuthHandler;
import com.demo.push.handler.DefaultInterceptor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.security.InvalidParameterException;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.Pattern;

/**
 * ClassName:DefaultApiClient
 * Package:com.demo.push.core
 * Description:
 *
 * @Date:2023/4/3 16:16
 * @Author:qs@1.com
 */
public class DefaultApiClient {
    private static Logger log = LoggerFactory.getLogger(DefaultApiClient.class);

    /**
     * key: {@link ApiConfiguration}, value: {@link DefaultApiClient}
     * 保证一个{@link ApiConfiguration}对象对应一个{@link DefaultApiClient}对象
     */
    private static ConcurrentMap<String, DefaultApiClient> cache = new ConcurrentHashMap<String, DefaultApiClient>(2);
    private final static Object CACHE_LOCK = new Object();

    /**
     * 配置信息
     */
    private final ApiConfiguration apiConfiguration;

    /**
     * 鉴权token数据
     */
    private TokenDTO token;

    final String CONTENT_TYPE = "application/json";


    /**
     * json转换
     */
    private IJson json;

    /**
     * 是否关闭
     */
    private boolean closed = false;

    private final HttpManager httpManager;


    public static DefaultApiClient build(ApiConfiguration apiConfiguration, IJson json) {
        apiConfiguration.check();
        if (json == null) {
            throw new InvalidParameterException("IJson 对象不能为null");
        }
        // serviceId
        String key = apiConfiguration.keyOfCache();
        DefaultApiClient defaultApiClient = cache.get(key);
        if (defaultApiClient == null) {
            synchronized (CACHE_LOCK) {
                defaultApiClient = cache.get(key);
                if (defaultApiClient == null) {
                    defaultApiClient = new DefaultApiClient(apiConfiguration, json);
                }
                cache.put(key, defaultApiClient);
            }
        }
        return defaultApiClient;
    }

    final static Pattern HIDE_MASTER_SECRET_PATTERN = Pattern.compile("(.{3}).+(.{3})");


    private DefaultApiClient(ApiConfiguration apiConfiguration, IJson json) {
        if (apiConfiguration == null) {
            throw new ApiException("apiConfiguration cannot be null.", true);
        }
        this.json = json;
        apiConfiguration.check();
        this.apiConfiguration = apiConfiguration;

        this.httpManager = new HttpManager(apiConfiguration.getConnectTimeout(),
                apiConfiguration.getSoTimeout(), apiConfiguration.getConnectionRequestTimeout(),
                apiConfiguration.getMaxHttpTryTime(), apiConfiguration.getKeepAliveSeconds(),
                apiConfiguration.getProxyConfig(), apiConfiguration.isTrustSSL());

        DefaultInterceptor defaultInterceptor = new DefaultInterceptor(apiConfiguration);
        addApiInterceptor(defaultInterceptor);
    }

    public void addApiInterceptor(ApiInterceptor apiInterceptor) {
        this.apiConfiguration.addApiInterceptor(apiInterceptor);
    }

    /**
     * 执行http调用之前调用此方法
     *
     * @param apiParam 请求参数
     * @param header   请求header
     * @param body     请求body
     */
    protected void beforeExecute(ApiProxyFactory.ApiParam apiParam, Map<String, Object> header, String body) {
        List<ApiInterceptor> interceptorList = this.apiConfiguration.getApiInterceptorList();
        if (CollectionUtils.isEmpty(interceptorList)) {
            return;
        }
        for (ApiInterceptor apiInterceptor : interceptorList) {
            apiInterceptor.pre(apiParam, header, body);
        }
    }

    /**
     * 1. 调用接口
     * 2. 如果返回token失效，刷新token后再次调用
     *
     * @param apiParam
     * @return
     */
    public ApiResult<?> execute(ApiProxyFactory.ApiParam apiParam) {
        return doExecute(apiParam);
    }

    /**
     * 执行HTTP调用并解析返回值
     *
     * @param apiParam
     * @return
     */
    private ApiResult<?> doExecute(ApiProxyFactory.ApiParam apiParam) {
        Map<String, Object> header = new HashMap<String, Object>(4);

        AuthHandler authHandler = apiConfiguration.getAuthHandler();
        authHandler.handle(apiConfiguration, header, apiParam);

        String body = null;
        if (apiParam.getBody() != null) {
            body = json.toJson(apiParam.getBody());
        }
        String result = null;
        String fullUrl = genFullUrl(apiParam.getUri(), apiParam.getPathParams(), apiParam.getQueryParams());
        try {
            // 处理header
            handleHeader(apiParam, header, body);
            beforeExecute(apiParam, header, body);
            result = httpManager.syncHttps(fullUrl, apiParam.getMethod(), header, body, CONTENT_TYPE);
            postExecute(apiParam, header, body, result);
        } catch (ApiException e) {
            handleException(apiParam, header, body, e);
            return ApiResult.fail(e.getMessage(), e.getCode());
        } finally {
            afterDoExecute(apiParam, header, body, result);
        }
        if (result == null) {
            throw new ApiException(String.format("请求失败，返回值为空。url:%s, body: %s.", fullUrl, body));
        }
        try {
            ApiResult<?> apiResult = json.fromJson(result, apiParam.getReturnType());
            if (apiResult.getCode() == 301) {
                // 兼容域名改变
                apiResult = json.fromJson(result, ApiResult.class);
            }
            if (apiResult == null) {
                throw new ApiException(String.format("请求失败，解析返回值失败。url:%s, body: %s, result: %s.", fullUrl, body, result));
            }
            return apiResult;
        } catch (Exception e) {
            // 兼容域名改变
            ApiResult<?> apiResult = json.fromJson(result, ApiResult.class);
            if (apiResult == null) {
                throw new ApiException(String.format("请求失败，解析返回值失败。url:%s, body: %s, result: %s.", fullUrl, body, result));
            }
            return apiResult;
        }
    }


    /**
     * 处理header参数
     *
     * @param header header参数
     * @return
     */
    private void handleHeader(ApiProxyFactory.ApiParam apiParam, Map<String, Object> header, String body) {
        List<ApiInterceptor> interceptorList = this.apiConfiguration.getApiInterceptorList();
        if (CollectionUtils.isEmpty(interceptorList)) {
            return;
        }
        for (ApiInterceptor apiInterceptor : interceptorList) {
            apiInterceptor.handleHeader(apiParam, header, body);
        }
    }

    /**
     * 调用远程接口后调用此方法，如果报错则不会调用
     *
     * @param apiParam http请求的参数
     * @param header   http请求的header
     * @param body     http请求的body
     */
    protected void postExecute(ApiProxyFactory.ApiParam apiParam, Map<String, Object> header, String body, String result) {
        List<ApiInterceptor> interceptorList = this.apiConfiguration.getApiInterceptorList();
        if (CollectionUtils.isEmpty(interceptorList)) {
            return;
        }
        for (ApiInterceptor apiInterceptor : interceptorList) {
            apiInterceptor.post(apiParam, header, body, result);
        }
    }

    /**
     * 调用远程接口报错后调用此方法
     *
     * @param apiParam http请求的参数
     * @param header   http请求的header
     * @param body     http请求的body
     * @param e        http请求抛出的异常
     */
    protected void handleException(ApiProxyFactory.ApiParam apiParam, Map<String, Object> header, String body, ApiException e) {
        List<ApiInterceptor> interceptorList = this.apiConfiguration.getApiInterceptorList();
        if (CollectionUtils.isEmpty(interceptorList)) {
            return;
        }
        for (ApiInterceptor apiInterceptor : interceptorList) {
            apiInterceptor.handleException(apiParam, header, body, e);
        }
    }

    /**
     * 调用接口后调用此方法
     *
     * @param apiParam http请求的参数
     * @param header   http请求的header
     * @param body     http请求的body
     * @param result   http请求的返回值
     */
    protected void afterDoExecute(ApiProxyFactory.ApiParam apiParam, Map<String, Object> header, String body, String result) {
        List<ApiInterceptor> interceptorList = this.apiConfiguration.getApiInterceptorList();
        if (CollectionUtils.isEmpty(interceptorList)) {
            return;
        }
        for (ApiInterceptor apiInterceptor : interceptorList) {
            apiInterceptor.afterCompletion(apiParam, header, body, result);
        }
    }



    /**
     * 生成完整的请求链接
     *
     * @param uri
     * @param pathParams
     * @param queryParams
     * @return
     */
    private String genFullUrl(String uri, String pathParams, List<String> queryParams) {
        String host = apiConfiguration.getDomain();

        if (StringUtils.isEmpty(pathParams)) {
            host = host + uri;
        } else {
            host += uri;
            if (host.endsWith("/")) {
                host += pathParams;
            } else {
                host = host + "/" + pathParams;
            }
        }
        if (CollectionUtils.isEmpty(queryParams)) {
            return host;
        }
        StringBuilder queryParamSb = new StringBuilder();
        for (String param : queryParams) {
            queryParamSb.append(param).append("&");
        }
        final String queryParam = queryParamSb.substring(0, queryParamSb.length() - 1);
        return host + "?" + queryParam;
    }

    public void setJson(IJson json) {
        this.json = json;
    }

    /**
     * 释放 {@link #cache}中的对象，但是此对象仍然可以使用，直到没有内存引用被回收
     */
    public void close() {
        this.closed = true;
    }


    public ApiConfiguration getApiConfiguration() {
        return apiConfiguration;
    }
}
