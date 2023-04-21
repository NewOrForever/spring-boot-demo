package com.demo.push;

import com.demo.push.common.Assert;
import com.demo.push.handler.ApiInterceptor;
import com.demo.push.handler.ApiInterceptorChain;
import com.demo.push.handler.AuthHandler;
import org.apache.http.client.config.RequestConfig;

import java.util.List;
import java.util.Objects;

/**
 * ClassName:MyApiConfiguration
 * Package:com.demo.push
 * Description: 应用相关配置信息
 *
 * @Date:2023/3/2 13:32
 * @Author:qs@1.com
 */
public class ApiConfiguration {
    /**
     * 应用id
     */
    private String appId;
    /**
     * 应用key
     */
    private String appKey;
    /**
     * 应用秘钥
     */
    private String appSecret;
    /**
     * 接口调用前缀
     */
    private String domain = "https://www.baidu.com/v2";

    /**
     * http请求读超时时间，单位ms
     *
     * @see RequestConfig#getSocketTimeout()
     */
    private int soTimeout = 30000;
    /**
     * http连接超时时间，单位ms
     *
     * @see RequestConfig#getConnectTimeout()
     */
    private int connectTimeout = 60000;
    /**
     * 从连接池中获取http连接的超时时间，单位ms
     */
    private int connectionRequestTimeout = 0;
    /**
     * http请求失败，最大尝试次数
     */
    private int maxHttpTryTime = 1;
    /**
     * 保持长连接的时长，最大{@link #MAX_KEEP_ALIVE_SECONDS}
     */
    private long keepAliveSeconds = 30;
    final long MAX_KEEP_ALIVE_SECONDS = 20 * 60;

    /**
     * http请求时是否需要信任https
     */
    private boolean trustSSL = false;

    /**
     * http请求设置代理，默认不设置
     */
    private HttpProxyConfig proxyConfig;

    /**
     * 服务唯一id，调用不同厂商的api时，api 的调用方式和鉴权方式都是不一样的
     * 所以对于不同厂商的api，都需要一个唯一id 进行数据的隔离
     */
    private String serviceId;

    private ApiInterceptorChain apiInterceptorChain;

    /**
     * 接口调用的鉴权处理器
     */
    private AuthHandler authHandler;


    /**
     * @param domain 接口调用前缀, 可不含{@link #appId}
     */
    public void setDomain(String domain) {
        Assert.notBlank(domain, true);
        this.domain = domain;
    }


    public void check() {
        Assert.notBlank(domain, true);
        Assert.notBlank(serviceId, true);
    }

    public boolean isTrustSSL() {
        return trustSSL;
    }

    public void setTrustSSL(boolean trustSSL) {
        this.trustSSL = trustSSL;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getDomain() {
        return domain;
    }


    public int getSoTimeout() {
        return soTimeout;
    }

    public void setSoTimeout(int soTimeout) {
        this.soTimeout = soTimeout;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public int getConnectionRequestTimeout() {
        return connectionRequestTimeout;
    }

    public void setConnectionRequestTimeout(int connectionRequestTimeout) {
        this.connectionRequestTimeout = connectionRequestTimeout;
    }

    public int getMaxHttpTryTime() {
        return maxHttpTryTime;
    }

    public void setMaxHttpTryTime(int maxHttpTryTime) {
        this.maxHttpTryTime = maxHttpTryTime;
    }

    public long getKeepAliveSeconds() {
        return keepAliveSeconds;
    }

    public void setKeepAliveSeconds(long keepAliveSeconds) {
        if (keepAliveSeconds > MAX_KEEP_ALIVE_SECONDS) {
            this.keepAliveSeconds = MAX_KEEP_ALIVE_SECONDS;
        } else {
            this.keepAliveSeconds = keepAliveSeconds;
        }
    }

    public HttpProxyConfig getProxyConfig() {
        return proxyConfig;
    }

    public void setProxyConfig(HttpProxyConfig proxyConfig) {
        this.proxyConfig = proxyConfig;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public AuthHandler getAuthHandler() {
        return authHandler;
    }

    public void setAuthHandler(AuthHandler authHandler) {
        this.authHandler = authHandler;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ApiConfiguration that = (ApiConfiguration) o;

        return serviceId.equals(that.serviceId);
    }

    @Override
    public int hashCode() {
        return serviceId.hashCode();
    }
    /**
     * 生成缓存的key
     *
     * @return 缓存key
     */
    public String keyOfCache() {
        check();
        return serviceId;
    }


    public void addApiInterceptor(ApiInterceptor apiInterceptor) {
        this.apiInterceptorChain.addInterceptor(apiInterceptor);
    }

    public List<ApiInterceptor> getApiInterceptorList() {
        return this.apiInterceptorChain.getInterceptorList();
    }
}
