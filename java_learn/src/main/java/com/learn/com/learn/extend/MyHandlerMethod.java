package com.learn.com.learn.extend;

/**
 * ClassName:MyHandlerMethod
 * Package:com.learn.com.learn.extend
 * Description:
 *
 * @Date:2022/5/18 10:16
 * @Author:qs@1.com
 */
public class MyHandlerMethod {
    private String beanName;

    public MyHandlerMethod() {
    }

    public MyHandlerMethod(MyHandlerMethod handlerMethod) {
        this.beanName = handlerMethod.beanName;
    }

    public MyHandlerMethod(String beanName) {
        this.beanName = beanName;
    }
}
