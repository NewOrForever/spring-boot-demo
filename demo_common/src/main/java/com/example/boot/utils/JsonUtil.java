package com.example.boot.utils;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

/**
 * ClassName:JsonUtil
 * Package:com.example.boot.utils
 * Description:
 *
 * @Date:2021/8/14 10:13
 * @Author:qs@1.com
 */
public class JsonUtil {
    private static ObjectMapper mapper = new ObjectMapper();

    /**
     *  json转对象
     * @param data
     * @return
     */
    public static String objectToJson(Object data){
        try {
            String result = mapper.writeValueAsString(data);
            return result;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *  json 转pojo
     * @param jsonString
     * @param beanType
     * @param <T>
     * @return
     */
    public static <T> T jsonToPoJo(String jsonString, Class<T> beanType){
        try {
            T result = mapper.readValue(jsonString, beanType);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *  json 转list
     * @param jsonList
     * @param beanType
     * @param <T>
     * @return
     */
    public static <T> List<T> jsonToList(String jsonList, Class<T> beanType){
        try {
            JavaType javaType= mapper.getTypeFactory().constructParametricType(List.class, beanType);
            List<T> result = mapper.readValue(jsonList, javaType);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
