package com.learn.proxy.jdkProxy;

/**
 * ClassName:JdkUser
 * Package:com.learn.proxy.jdkProxy
 * Description: 目标对象
 *
 * @Date:2021/12/17 15:24
 * @Author:qs@1.com
 */
public class JdkUser implements IJdkUser{
    @Override
    public void execute() {
        System.out.println("这是目标对象：" + this.toString());
    }
}
