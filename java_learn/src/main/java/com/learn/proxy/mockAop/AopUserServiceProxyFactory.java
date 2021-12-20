package com.learn.proxy.mockAop;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * ClassName:AopUserServiceFactory
 * Package:com.learn.proxy.mockAop
 * Description:
 *
 * @Date:2021/12/20 12:57
 * @Author:qs@1.com
 */
public class AopUserServiceProxyFactory implements MethodInterceptor {

    private AopUserService target;
    private Aop aop;

    public AopUserServiceProxyFactory(AopUserService target, Aop aop) {
        this.target = target;
        this.aop = aop;
    }

    public Object getProxyInstance() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(AopUserService.class);
        enhancer.setCallback(this);
        return enhancer.create();
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {

        aop.beforeExecute();
        Object result = method.invoke(target, args);
        aop.afterExecute();
        return result;
    }
}
