package com.demo.push.core;

import com.demo.push.annotation.param.BodyParam;
import com.demo.push.annotation.param.PathParam;
import com.demo.push.annotation.param.QueryParam;
import com.demo.push.common.ApiException;
import com.demo.push.common.Assert;
import com.demo.push.core.registry.DefaultApiRegistry;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * ClassName:ApiProxyFactory
 * Package:com.demo.push.core
 * Description:
 *
 * @Date:2023/3/2 14:47
 * @Author:qs@1.com
 */
public class ApiProxyFactory {
    /**
     * 保证一个{@link DefaultApiClient}对象对应一个{@link ApiProxyFactory}对象
     */
    private static ConcurrentMap<DefaultApiClient, ApiProxyFactory> cache = new ConcurrentHashMap<>(2);

    DefaultApiClient defaultApiClient;

    /**
     * 缓存接口的相关数据，减小解析成本
     */
    private DefaultApiRegistry defaultApiRegistry;

    public void setDefaultApiRegistry(DefaultApiRegistry defaultApiRegistry) {
        this.defaultApiRegistry = defaultApiRegistry;
    }

    public static ApiProxyFactory build(DefaultApiClient defaultApiClient) {
        Assert.notNull(defaultApiClient, "DefaultApiClient");

        ApiProxyFactory apiProxyFactory = cache.get(defaultApiClient);
        if (apiProxyFactory == null) {
            synchronized (cache) {
                apiProxyFactory = cache.get(defaultApiClient);
                if (apiProxyFactory == null) {
                    apiProxyFactory = new ApiProxyFactory(defaultApiClient);
                    cache.put(defaultApiClient, apiProxyFactory);
                }
            }
        }
        return apiProxyFactory;
    }

    private ApiProxyFactory(DefaultApiClient defaultApiClient) {
        if (defaultApiClient == null) {
            throw new ApiException("defaultApiClient cannot be null.", true);
        }
        this.defaultApiClient = defaultApiClient;
        defaultApiRegistry = new DefaultApiRegistry();
    }

    /**
     * 创建代理对象
     *
     * @param apiService
     * @param <T>
     * @return
     */
    public <T> T createProxy(Class<T> apiService) {
        return (T) Proxy.newProxyInstance(apiService.getClassLoader(), new Class[]{apiService}, new ApiProxyHandler());
    }


    class ApiProxyHandler implements InvocationHandler {
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) {
            try {
                if (Object.class.equals(method.getDeclaringClass())) {
                    return method.invoke(this, args);
                }
            } catch (Throwable t) {
                throw new RuntimeException(t);
            }

            final BaseParam baseParam = defaultApiRegistry.get(defaultApiClient.getApiConfiguration().getServiceId(), method);
            ApiParam apiParam = new ApiParam(baseParam);
            // 解析参数 -> HTTP参数
            handleApiParam(method, args, apiParam);
            return defaultApiClient.execute(apiParam);
        }
    }

    /**
     * 处理参数, HTTP调用路径参数和body参数
     *
     * @param method 用于获取方法上的注解
     * @param args
     * @return notnull
     */
    private void handleApiParam(Method method, Object[] args, ApiParam apiParam) {
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        for (int i = 0; i < parameterAnnotations.length; i++) {
            for (Annotation annotation : parameterAnnotations[i]) {
                if (annotation instanceof PathParam) {
                    apiParam.handlePathParam(args[i]);
                } else if (annotation instanceof QueryParam) {
                    apiParam.handleQueryParam(args[i], ((QueryParam) annotation).name());
                } else if (annotation instanceof BodyParam) {
                    apiParam.setBody(args[i]);
                }
            }
        }
    }

    /**
     * 释放 {@link #cache}中的对象，但是此对象仍然可以使用，直到没有内存引用被回收
     */
    public void close() {
        this.defaultApiClient.close();
        cache.remove(this.defaultApiClient);
    }

    /**
     * HTTP请求的参数
     */
    public static class ApiParam {
        /**
         * 基础参数，从方法注解中解析
         */
        private final BaseParam baseParam;
        /**
         * 路径参数
         */
        private String pathParams;
        /**
         * query参数
         */
        private List<String> queryParams;
        /**
         * body参数
         */
        private Object body;

        public ApiParam(BaseParam baseParam) {
            this.baseParam = baseParam;
        }

        /**
         * 处理路径参数
         *
         * @param arg
         */
        public void handlePathParam(Object arg) {
            Assert.notNull(arg, "路径参数");
            setPathParams(handleArg(arg));
        }

        private void addQueryParams(String name, String param) {
            if (queryParams == null) {
                queryParams = new ArrayList<String>();
            }
            queryParams.add(name + "=" + param);
        }

        public void handleQueryParam(Object arg, String name) {
            Assert.notNull(arg, "query参数");
            final String param = handleArg(arg);
            addQueryParams(name, param);
        }

        private String handleArg(Object arg) {
            if (arg instanceof Iterable) {
                final Iterator iterator = ((Iterable) arg).iterator();
                StringBuilder sb = new StringBuilder();
                while (iterator.hasNext()) {
                    sb.append(iterator.next()).append(',');
                }
                String param = sb.toString();
                if (param.endsWith(",")) {
                    param = param.substring(0, param.length() - 1);
                }
                return param;
            } else if (arg instanceof Number) {
                return arg.toString();
            } else if (arg instanceof String) {
                return (String) arg;
            } else {
                throw new ApiException("路径参数(加PathParam注解的参数)和query参数(加QueryParam注解的参数)只能为 Iterable/Number/String的三种类型或其子类型");
            }
        }

        public String getUri() {
            return baseParam.getUri();
        }

        public String getMethod() {
            return baseParam.getMethod();
        }

        public Boolean getNeedToken() {
            return baseParam.getNeedToken();
        }

        public Type getReturnType() {
            return baseParam.getReturnType();
        }

        public BaseParam getBaseParam() {
            return baseParam;
        }

        public String getPathParams() {
            return pathParams;
        }

        public void setPathParams(String pathParams) {
            this.pathParams = pathParams;
        }

        public List<String> getQueryParams() {
            return queryParams;
        }

        public void setQueryParams(List<String> queryParams) {
            this.queryParams = queryParams;
        }

        public Object getBody() {
            return body;
        }

        public void setBody(Object body) {
            this.body = body;
        }


        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("ApiParam{");
            sb.append("baseParam=").append(baseParam);
            sb.append(", pathParams='").append(pathParams).append('\'');
            sb.append(", queryParams=").append(queryParams);
            sb.append(", body=").append(body);
            sb.append('}');
            return sb.toString();
        }
    }

    /**
     * HTTP请求的参数
     */
    public static class BaseParam {
        /**
         * 接口调用相对路径
         * eg. /auth
         */
        private String uri;
        /**
         * 接口请求方式 GET/POST/PUT/DELETE
         */
        private String method;

        /**
         * 是否需要token
         */
        private Boolean needToken;

        private boolean isAuth = false;

        /**
         * 返回值类型
         */
        private Type returnType;

        public boolean isAuth() {
            return isAuth;
        }

        public void setAuth(boolean auth) {
            isAuth = auth;
        }

        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public Boolean getNeedToken() {
            return needToken;
        }

        public void setNeedToken(Boolean needToken) {
            this.needToken = needToken;
        }

        public Type getReturnType() {
            return returnType;
        }

        public void setReturnType(Type returnType) {
            this.returnType = returnType;
        }

        @Override
        public String toString() {
            return "BaseParam{" +
                    "uri='" + uri + '\'' +
                    ", method='" + method + '\'' +
                    ", needToken=" + needToken +
                    ", returnType=" + returnType +
                    '}';
        }
    }


}
