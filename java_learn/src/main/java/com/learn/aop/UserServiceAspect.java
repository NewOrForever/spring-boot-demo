package com.learn.aop;

import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

/**
 * ClassName:UserServiceAspect
 * Package:com.learn.aop
 * Description:
 *
 * @Date:2021/12/20 14:32
 * @Author:qs@1.com
 */
@Component
//@EnableAspectJAutoProxy
public class UserServiceAspect {

    public void save() {
        System.out.println("核心业务：保存。。。");
    }

}
