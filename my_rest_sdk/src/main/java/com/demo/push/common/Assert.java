package com.demo.push.common;


import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;

/**
 * ClassName:Assert
 * Package:com.demo.push.common
 * Description:
 *
 * @Date:2023/3/2 13:37
 * @Author:qs@1.com
 */
public class Assert {
    public static void notEmpty(String param, String paramName, boolean writableStackTrace) {
        if (StringUtils.isEmpty(param)) {
            throw new ApiException(paramName + " 不能为空", 400, writableStackTrace);
        }
    }

    public static void notBlank(String param, String paramName) {
        if (StringUtils.isBlank(param)) {
            throw new ApiException(paramName + " 不能为空", 400);
        }
    }

    public static void notBlank(String param) {
        if (StringUtils.isBlank(param)) {
            throw new ApiException("参数不能为空", 400);
        }
    }

    public static void notBlank(String param, boolean writableStackTrace) {
        if (StringUtils.isBlank(param)) {
            throw new ApiException("参数不能为空", 400, writableStackTrace);
        }
    }

    public static <T> void notEmpty(Collection<T> collection, String paramName) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new ApiException(paramName + " 不能为空", 400);
        }
    }

    public static <T> void notEmpty(Collection<T> collection, String paramName, boolean writableStackTrace) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new ApiException(paramName + " 不能为空", 400, writableStackTrace);
        }
    }

    public static void notNull(Object obj, String paramName) {
        if (obj == null) {
            throw new ApiException(paramName + " 不能为null", 400);
        }
    }
}
