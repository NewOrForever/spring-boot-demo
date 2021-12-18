package com.learn.atomic;

/**
 * ClassName:com.learn.atomic.Main
 * Package:PACKAGE_NAME
 * Description:
 *
 * @Date:2021/12/14 14:28
 * @Author:qs@1.com
 */
public class AtomicMain {
    public static void main(String[] args) {
        // test ProblematicLock
        //com.learn.atomic.ProlematicLock lock = new com.learn.atomic.ProlematicLock();
//        com.learn.atomic.CompareAndSwapLock lock = new com.learn.atomic.CompareAndSwapLock();
//
//        for (int i = 0; i < 50; i++) {
//
//            com.learn.atomic.MyThread myThread = new com.learn.atomic.MyThread(lock, "thread" + i);
//            myThread.start();
//        }

        //  模拟乐观锁用于计数器
        OptimisticLockCounter optimisticLockCounter = new OptimisticLockCounter();
        for (int i = 0; i < 100; i++) {
            new Thread(){
                @Override
                public void run() {
                    optimisticLockCounter.inc();
                }
            }.start();
        }

    }
}
