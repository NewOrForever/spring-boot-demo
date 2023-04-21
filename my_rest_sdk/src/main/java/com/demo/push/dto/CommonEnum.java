package com.demo.push.dto;

/**
 * ClassName:CommonEnum
 * Package:com.demo.push.dto
 * Description:
 *
 * @Date:2023/4/6 15:46
 * @Author:qs@1.com
 */
public interface CommonEnum {
    enum MethodEnum {
        METHOD_GET("GET"),
        METHOD_POST("POST"),
        METHOD_PUT("PUT"),
        METHOD_DELETE("DELETE"),
        METHOD_PATCH("PATCH"),
        METHOD_TRACE("TRACE"),
        METHOD_HEAD("HEAD"),
        METHOD_OPTIONS("OPTIONS"),
        ;
        public final String method;

        MethodEnum(String method) {
            this.method = method;
        }

        public boolean is(String method) {
            return this.method.equalsIgnoreCase(method);
        }
    }

    /**
     * 条件关联方式
     */
    enum OptTypeEnum implements IEnum<String> {
        TYPE_AND("and"),
        TYPE_OR("or"),
        TYPE_NOT("not"),
        ;
        public final String type;

        @Override
        public boolean is(String s) {
            return get().equalsIgnoreCase(s);
        }

        @Override
        public String get() {
            return type;
        }

        OptTypeEnum(String type) {
            this.type = type;
        }
    }

    interface IEnum<T> {
        /**
         * 判断当前值和枚举值是否相同
         *
         * @param t
         * @return
         */
        boolean is(T t);

        /**
         * 获取枚举的值
         *
         * @return
         */
        T get();
    }

}
