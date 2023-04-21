package com.demo.push.core;

import com.demo.push.IJson;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;

/**
 * ClassName:DefaultJson
 * Package:com.demo.push.core
 * Description:
 *
 * @Date:2023/4/3 17:21
 * @Author:qs@1.com
 */
public class DefaultJson implements IJson {
    private static final Gson GSON = createGson();

    public static Gson createGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        return gsonBuilder.create();
    }


    @Override
    public String toJson(Object obj) {
        if (obj == null) {
            return null;
        }
        return GSON.toJson(obj);
    }

    @Override
    public <T> T fromJson(String jsonString, Type type) {
        return GSON.fromJson(jsonString, type);
    }

    @Override
    public <T> T fromJson(String jsonString, Class<T> tClass) {
        return GSON.fromJson(jsonString, tClass);
    }

}
