package com.learn.com.learn.extend;

/**
 * ClassName:MyInvocableHandlerMethod
 * Package:com.learn.com.learn.extend
 * Description:
 *
 * @Date:2022/5/18 10:18
 * @Author:qs@1.com
 */
public class MyInvocableHandlerMethod extends MyHandlerMethod {
    private String invocableName;

    public MyInvocableHandlerMethod(MyHandlerMethod handlerMethod) {
        super(handlerMethod);
    }

    public MyInvocableHandlerMethod(String beanName) {
        super(beanName);
    }

    public MyInvocableHandlerMethod() {
    }

    public static void main(String[] args) {
        MyHandlerMethod handlerMethod = new MyHandlerMethod("myBeanName");
        MyInvocableHandlerMethod myInvocableHandlerMethod = new MyInvocableHandlerMethod(handlerMethod);

        MyInvocableHandlerMethod seond = new MyInvocableHandlerMethod("seond");

        MyInvocableHandlerMethod third = new MyInvocableHandlerMethod();

        System.out.println();

    }

}
