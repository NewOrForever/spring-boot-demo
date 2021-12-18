package com.learn.proxy.staticProxy;

/**
 * ClassName:UserServiceProxy
 * Package:com.learn.proxy.staticProxy
 * Description:
 *
 * @Date:2021/12/17 14:23
 * @Author:qs@1.com
 */
public class UserServiceProxy implements IUserService{
    private IUserService target;

    public UserServiceProxy(IUserService target) {
        this.target = target;
    }

    @Override
    public void execute() {
        System.out.println("事务开始。。。");

        target.execute();

        System.out.println("事务结束。。。");
    }
}
