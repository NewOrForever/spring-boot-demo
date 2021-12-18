package com.learn.atomic;

import java.util.concurrent.atomic.AtomicLong;

/**
 * ClassName:com.learn.atomic.OptimisticLockCounter
 * Package:PACKAGE_NAME
 * Description: 使用Atomic的compareAndSet的原子性来模拟进行多线程情况下的计数器实现（乐观锁）
 *
 * @Date:2021/12/16 14:11
 * @Author:qs@1.com
 */
public class OptimisticLockCounter {
    // 共享数据，计数器当前值
    private AtomicLong count = new AtomicLong();

    /**
     * 自增操作
     * 模拟乐观锁
     *  An optimistic locking mechanism allows more than one thread to enter a critical section at a time, but only allows
     *  one of the threads to commit its work at the end of the critical section.
     */
    public void inc() {
        boolean successfull = false;
        while(!successfull){
            long curCount = getCount();
            long newCount = curCount + 1;
            successfull = this.count.compareAndSet(curCount, newCount);
        }

        System.out.println(Thread.currentThread().getName()+ "，当前机器数值：" + getCount());
    }

    public long getCount() {
        return this.count.get();
    }
}
