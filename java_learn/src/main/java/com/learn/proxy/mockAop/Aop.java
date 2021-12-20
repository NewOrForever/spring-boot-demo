package com.learn.proxy.mockAop;

/**
 * ClassName:Aop
 * Package:com.learn.proxy.mockAop
 * Description:
 *
 * @Date:2021/12/20 12:55
 * @Author:qs@1.com
 */
public class Aop {

    public void beforeExecute() {
        System.out.println("方法执行before。。。。。。");
    }

    public void afterExecute() {
        System.out.println("方法执行after。。。。。。");
    }
}
