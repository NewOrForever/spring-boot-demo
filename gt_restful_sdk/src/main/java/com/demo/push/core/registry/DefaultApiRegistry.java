package com.demo.push.core.registry;

import com.demo.push.annotation.method.MyDelete;
import com.demo.push.annotation.method.MyGet;
import com.demo.push.annotation.method.MyPost;
import com.demo.push.annotation.method.MyPut;
import com.demo.push.common.ApiException;
import com.demo.push.common.type.ParameterizedTypeImpl;
import com.demo.push.common.type.TypeReference;
import com.demo.push.core.ApiProxyFactory;
import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ClassName:DefaultApiRegistry
 * Package:com.demo.push.core
 * Description:
 *
 * @Date:2023/4/3 16:20
 * @Author:qs@1.com
 */
public class DefaultApiRegistry implements ApiRegistry {
    private Map<String, ApiProxyFactory.BaseParam> cache = new ConcurrentHashMap<>();

    @Override
    public void register(Method method) {
        get(method);
    }

    @Override
    public ApiProxyFactory.BaseParam get(Method method) {
        ApiProxyFactory.BaseParam param = cache.get(method.toString());
        if (param != null) {
            return param;
        }
        synchronized (cache) {
            param = cache.get(method.toString());
            if (param != null) {
                return param;
            }
            param = doAnalise(method);
            cache.put(method.toString(), param);
            return param;
        }
    }

    private ApiProxyFactory.BaseParam doAnalise(Method method) {
        ApiProxyFactory.BaseParam param = new ApiProxyFactory.BaseParam();
        // 解析方法注解 -> HTTP请求方式和uri
        handleAnnotation(method.getAnnotations(), param);
        // 获取泛型类型，用于反序列化使用
        Type[] types = ((ParameterizedType) method.getGenericReturnType()).getActualTypeArguments();
        // 设置返回值类型，用于反序列化
        param.setReturnType(new MyTypeHelper(method.getReturnType(), types).getType());
        return param;
    }

    /**
     * 这个类主要目的: 构造泛型类型，方便反序列化(参考jackson)
     * 不一定是最好的方式，如果有更好的方式，可以替换
     */
    class MyTypeHelper extends TypeReference<Void> {
        final Class<?> aClass;
        final Type[] types;

        public MyTypeHelper(Class<?> aClass, Type[] types) {
            this.aClass = aClass;
            this.types = types;
        }

        @Override
        public Type getType() {
            return ParameterizedTypeImpl.make(aClass, types, null);
        }
    }

    /**
     * 处理注解，解析uri和method
     *
     * @param annotations
     * @param apiParam    notnull
     */
    private ApiProxyFactory.BaseParam handleAnnotation(Annotation[] annotations, ApiProxyFactory.BaseParam apiParam) {
        if (apiParam == null) {
            throw new ApiException("apiParam cannot be null.");
        }
        for (Annotation annotation : annotations) {
            if (annotation instanceof MyGet) {
                apiParam.setMethod("GET");
                apiParam.setUri(((MyGet) annotation).uri());
                apiParam.setNeedToken(((MyGet) annotation).needToken());
            } else if (annotation instanceof MyPost) {
                apiParam.setMethod("POST");
                apiParam.setUri(((MyPost) annotation).uri());
                apiParam.setNeedToken(((MyPost) annotation).needToken());
            } else if (annotation instanceof MyPut) {
                apiParam.setMethod("PUT");
                apiParam.setUri(((MyPut) annotation).uri());
                apiParam.setNeedToken(((MyPut) annotation).needToken());
            } else if (annotation instanceof MyDelete) {
                apiParam.setMethod("DELETE");
                apiParam.setUri(((MyDelete) annotation).uri());
                apiParam.setNeedToken(((MyDelete) annotation).needToken());
            } else {
                throw new ApiException("请添加请求注解 MyGet/MyPost/MyPut/MyDelete");
            }
        }
        if (StringUtils.isEmpty(apiParam.getMethod())) {
            throw new UnsupportedOperationException();
        }
        return apiParam;
    }

}
