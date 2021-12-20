package com.learn.proxy;

import com.learn.proxy.cglib.CgUser;
import com.learn.proxy.cglib.CgUserProxyFactory;
import com.learn.proxy.jdkProxy.IJdkUser;
import com.learn.proxy.jdkProxy.JdkUser;
import com.learn.proxy.jdkProxy.JdkUserProxyFactory;
import com.learn.proxy.mockAop.Aop;
import com.learn.proxy.mockAop.AopUserService;
import com.learn.proxy.mockAop.AopUserServiceProxyFactory;
import com.learn.proxy.staticProxy.User;
import com.learn.proxy.staticProxy.UserProxy;
import com.learn.proxy.staticProxy.UserService;
import com.learn.proxy.staticProxy.UserServiceProxy;

/**
 * ClassName:main
 * Package:com.learn.proxy.staticProxy.proxy
 * Description: 代理测试
 *
 * @Date:2021/12/17 14:33
 * @Author:qs@1.com
 */
public class ProxyMain {
    public static void main(String[] args) {

        /**
         * 静态代理
         * 主体对象和代理对象实现同一个接口
         * 代理对象维护了一个主体对象
         * 代理对象执行方法的时候对主体对象的方法进行扩展
         */
        UserServiceProxy userServiceProxy = new UserServiceProxy(new UserService());
        userServiceProxy.execute();

        /**
         * spring源码：返回的目标对象有实现接口则使用jdk代理，没有实现接口则使用cglib代理
         * 模拟cglib子类代理
         * 主体对象是代理对象的父类
         * 代理对象维护了一个主体对象
         * 代理对象执行方法的时候对主体对象的方法进行扩展
         */
        User user = new UserProxy(new User());
        user.execute();

        /**
         *动态代理
         * 目标对象有实现接口，代理对象不需要实现接口
         * 代理对象维护目标对象
         * 使用jdk自带的api动态的在内存中构建代理对象
         */
        JdkUserProxyFactory jdkUserProxyFactory = new JdkUserProxyFactory(new JdkUser());
        IJdkUser proxyInstance = (IJdkUser) jdkUserProxyFactory.getProxyInstance();
        System.out.println(proxyInstance);
        proxyInstance.execute();

        /**
         * cglib子类代理
         * 引入cglib包
         * 代理对象维护子类对象，实现MethodIntercept，重写方法
         * 使用enhancer工具类创建代理对象
         */
        CgUser cgUserProxy = (CgUser)new CgUserProxyFactory(new CgUser()).getProxyInstance();
        // 代理对象执行的所有方法应该都是会拦截的
        System.out.println(cgUserProxy);
        cgUserProxy.execute();

        /**
         * 使用cglib动态代理来简易的模拟一个springaop
         * 就是增加了一个切面类
         */
        AopUserService aopUserServiceProxy = (AopUserService)new AopUserServiceProxyFactory(new AopUserService(), new Aop()).getProxyInstance();
        aopUserServiceProxy.execute();
    }


}
