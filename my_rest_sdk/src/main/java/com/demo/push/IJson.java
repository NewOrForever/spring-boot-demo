package com.demo.push;

import java.lang.reflect.Type;

/**
 * ClassName:IJson
 * Package:com.demo.push.annotation
 * Description:
 *
 * @Date:2023/4/3 17:20
 * @Author:qs@1.com
 */
public interface IJson {
    /**
     * 对象转json
     *
     * @param obj
     * @return
     */
    String toJson(Object obj);

    /**
     * json转对象
     *
     * @param jsonString
     * @param type
     * @param <T>
     * @return
     */
    <T> T fromJson(String jsonString, Type type);

    <T> T fromJson(String jsonString, Class<T> tClass);
}
