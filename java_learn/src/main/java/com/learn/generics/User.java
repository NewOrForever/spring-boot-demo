package com.learn.generics;

import org.aspectj.weaver.ast.Or;

import javax.annotation.Resource;

/**
 * ClassName:User
 * Package:com.learn.generics
 * Description:
 *
 * @Date:2022/1/12 16:20
 * @Author:qs@1.com
 */
public class User extends BaseService<Order, Stock>{
    public void test() {
        System.out.println(o);
    }
}
