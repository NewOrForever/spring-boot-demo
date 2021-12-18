package com.learn.atomic;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * ClassName:com.learn.atomic.CompareAndSwapLock
 * Package:PACKAGE_NAME
 * Description: 使用Atomic的compareAndSet来实现锁，使用这个的好处是可以调动服务器cpu来帮助调度线程，可以是线程的
 * 阻塞时间缩短（相比synchonized的一直阻塞直到os或者execution platform来调度，cas会不断的发起访问直到非阻塞），这样可以
 * 导致更少的阻塞和更多的吞吐量
 *
 * @Date:2021/12/16 13:25
 * @Author:qs@1.com
 */
public class CompareAndSwapLock implements ILock {

    private AtomicBoolean locked = new AtomicBoolean(false);

    public void lock() {
        while (!this.locked.compareAndSet(false, true)){
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // busy wait  until compareAndSet succeed
        }
    }

    public void unlock() {
        this.locked.set(false);
    }

}
