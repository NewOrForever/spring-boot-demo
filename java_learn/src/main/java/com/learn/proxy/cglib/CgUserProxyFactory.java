package com.learn.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * ClassName:CgUserProxy
 * Package:com.learn.proxy.cglib
 * Description:
 *
 * @Date:2021/12/17 16:37
 * @Author:qs@1.com
 */
public class CgUserProxyFactory implements MethodInterceptor {
    private CgUser target;

    public CgUserProxyFactory(CgUser target) {
        this.target = target;
    }

    public Object getProxyInstance(){
        Enhancer enhancer = new Enhancer();
        // 设置父类
        enhancer.setSuperclass(target.getClass());
        // 设置回调函数（拦截器）
        enhancer.setCallback(this);
        // 创建代理对象
        return enhancer.create();

    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("cglib代理开始。。。");
        System.out.println("执行方法：" + method.getName());
        Object result = method.invoke(target, objects);
//        System.out.println(result);
        System.out.println("cglib代理结束。。。");
        return result;
    }

}
