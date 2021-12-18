package com.learn.proxy.staticProxy;

/**
 * ClassName:UserService
 * Package:com.learn.proxy.staticProxy
 * Description:
 *
 * @Date:2021/12/17 14:22
 * @Author:qs@1.com
 */
public class UserService implements IUserService{

    @Override
    public void execute() {
        System.out.println(this);
    }
}
