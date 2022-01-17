package com.learn.aop;

import org.aspectj.lang.ProceedingJoinPoint;

import org.aspectj.lang.annotation.*;

import org.springframework.stereotype.Component;

/**
 * ClassName:MyAspect
 * Package:com.learn.aop
 * Description:
 *@Before("pointCut_()") 前置通知: 目标方法之前执行
 * @After("pointCut_()") 后置通知：目标方法之后执行（始终执行）
 * @AfterReturning("pointCut_()") 返回后通知： 执行方法结束后执行(异常不执行)
 * @AfterThrowing("pointCut_()") 异常通知:  出现异常时候执行
 * @Around("pointCut_()") 环绕通知： 环绕目标方法执行
 * @Date:2021/12/20 14:25
 * @Author:qs@1.com
 */
@Aspect
@Component
public class MyAspect {
    @Pointcut("execution(* com.learn.aop.*.*(..))")
    public void point_cut(){
    }

    @Before("point_cut()")
    public void before() {
        System.out.println("before。。。");
    }

    @After("point_cut()")
    public void after() {
        System.out.println("after。。。");
    }

    @AfterReturning("point_cut()")
    public void afterReturning() {
        System.out.println("afterReturning。。。");
    }

    @AfterThrowing("point_cut()")
    public void afterThrowing() {
        System.out.println("afterThrowing。。。");
    }

    @Around("point_cut()")
    public void around(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("around before。。。");
        pjp.proceed();
        System.out.println("around after。。。");

    }
}
