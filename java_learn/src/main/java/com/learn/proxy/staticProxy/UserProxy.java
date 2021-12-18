package com.learn.proxy.staticProxy;

/**
 * ClassName:UserProxy
 * Package:com.learn.proxy.staticProxy
 * Description:
 *
 * @Date:2021/12/17 14:42
 * @Author:qs@1.com
 */
public class UserProxy extends User{
    private User target;

    public UserProxy(User target) {
        this.target = target;
    }

    @Override
    public void execute() {
        System.out.println("事务开始。。。。。。");
        target.execute();
        System.out.println("事务结束。。。。。。");
    }
}
