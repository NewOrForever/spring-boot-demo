package com.learn.atomic;

/**
 * ClassName:com.learn.atomic.MyThread
 * Package:PACKAGE_NAME
 * Description:
 *
 * @Date:2021/12/14 15:37
 * @Author:qs@1.com
 */
public class MyThread extends Thread{
    private ILock lock;

    public MyThread(ILock lock, String name) {
        super(name);
        this.lock = lock;
    }

    @Override
    public void run() {
        lock.lock();
        System.out.println(Thread.currentThread().getName() + "：获取锁");
    }
}
