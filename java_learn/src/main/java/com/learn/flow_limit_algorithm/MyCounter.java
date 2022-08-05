package com.learn.flow_limit_algorithm;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * ClassName:MyCounter
 * Package:PACKAGE_NAME
 * Description: 一个简单的计数器限流算法
 * <p>
 * _____COUNTER______|_____COUNTER______
 * 0min                          1min                           2min
 * <p>
 * 设置一个计数器：
 * - 每当一个请求过来的时候，counter就加1，当counter 大于设定的阈值100并且该请求与第一个请求的时间间隔还在1min内，那么限流
 * - 当时间间隔超过1min重置计数器
 *
 * @Date:2022/8/3 10:47
 * @Author:qs@1.com
 */
public class MyCounter {
    private long timestamp = System.currentTimeMillis();    // 当前时间
    private int reqCount = 0;                                                   // 初始化计数器
    private final int limit = 100;                                              // 时间窗口内最大请求书
    private final long timeInterval = 60 * 1000;                     // 时间窗口

    public boolean limit() {
        long now = System.currentTimeMillis();
        if (now < timestamp + timeInterval) {
            // 当前时间在时间窗口内
            reqCount++;
            System.out.println("reqCount=" + reqCount + " , pass=" + (reqCount <= limit));
            // 判断当前时间窗口内是否超过最大请求控制数
            return reqCount <= limit;
        } else {
            // 不在时间窗口内 -> 超时重置计数器Q
            timestamp = now;
            reqCount = 1;
            return true;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        MyCounter counter = new MyCounter();
        while (true) {
            boolean pass = counter.limit();

            TimeUnit.MILLISECONDS.sleep(new Random().nextInt(600));
        }
    }

}
