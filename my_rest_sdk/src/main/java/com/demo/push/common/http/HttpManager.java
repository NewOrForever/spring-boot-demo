package com.demo.push.common.http;

import com.demo.push.HttpProxyConfig;

import java.util.Map;

/**
 * ClassName:HttpManager
 * Package:com.demo.push.http
 * Description:
 *
 * @Date:2023/4/3 17:58
 * @Author:qs@1.com
 */
public class HttpManager {
    private MyHttpClient client;

    /**
     * @param connectionTimeOut        连接超时时间
     * @param readTimeout              读超时时间
     * @param connectionRequestTimeout 从连接池中获取连接的超时时间
     * @param maxHttpTryTime           失败最大尝试次数
     * @param trustSSL
     */
    public HttpManager(int connectionTimeOut, int readTimeout, int connectionRequestTimeout, int maxHttpTryTime, long keepAliveSeconds, HttpProxyConfig proxyConfig, boolean trustSSL) {
        this.client = new MyHttpClient(connectionTimeOut, readTimeout, connectionRequestTimeout, maxHttpTryTime, keepAliveSeconds, proxyConfig, trustSSL);
    }

    public String syncHttps(String url, String method, Map<String, Object> headers, String body, String contentType) {
        return client.execute(url, method, headers, body, contentType);
    }
}
