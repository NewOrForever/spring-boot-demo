package com.learn.atomic;

/**
 * ClassName:com.learn.atomic.ProlematicLock
 * Package:PACKAGE_NAME
 * Description:
 *
 * @Date:2021/12/14 14:29
 * @Author:qs@1.com
 */
public class ProlematicLock implements ILock {
    private volatile boolean locked = false;

    public synchronized void lock() {
        while (this.locked){
            //System.out.println("==================waiting：" + Thread.currentThread().getName() + "阻塞中。。。");
            // busy wait - until this.locked == false
        }
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.locked = true;

    }

    public void unlock() {
        this.locked = false;
    }

    public boolean isLocked() {
        return locked;
    }


}
