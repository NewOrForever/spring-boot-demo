package com.learn.aop;

import com.learn.config.AppConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * ClassName:aspectMain
 * Package:com.learn.aop
 * Description:
 *
 * @Date:2021/12/20 14:42
 * @Author:qs@1.com
 */
public class AspectMain {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        UserServiceAspect userServiceAspect = context.getBean(UserServiceAspect.class);

        System.out.println(userServiceAspect.getClass());
        //System.out.println(userServiceAspect);

        userServiceAspect.save();

    }
}
