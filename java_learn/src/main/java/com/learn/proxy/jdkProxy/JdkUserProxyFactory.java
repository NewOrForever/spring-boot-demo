package com.learn.proxy.jdkProxy;

import org.omg.CORBA.ObjectHelper;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * ClassName:JdkUserProxy
 * Package:com.learn.proxy.jdkProxy
 * Description: 代理对象
 *
 * @Date:2021/12/17 15:26
 * @Author:qs@1.com
 */
public class JdkUserProxyFactory {
    private IJdkUser target;

    public JdkUserProxyFactory(IJdkUser target) {
        this.target = target;
    }

    public Object getProxyInstance(){
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        System.out.println("jdk动态代理开始。。。");
                        Object result = method.invoke(target, args);
                        System.out.println("jdk动态代理结束。。。");
                        return result;
                    }
                });
    }
}
