package com.learn.flow_limit_algorithm;

import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * ClassName:MySlidingTimeWindow
 * Package:com.learn.flow_limit_algorithm
 * Description: 一个滑动时间窗口算法的demo
 * MyCounter 有啥问题？0-30s：20个请求，30-60s：60个请求，60-90s：60个请求，所以30-90s这个区间是限流没用的。
 * 时间窗口比较大，一个窗口走完直接重置走下一个窗口
 * _________________|_________________
 * 0s         30         60s          90        120s
 * <p>
 * 基于上，如何优化？一个大的窗口拆分成一个个小窗格，每个窗格都有自己的计数器。
 * 1s ----> 10个窗格，每个100ms
 * 当当前时间在1000ms-1100ms，则0-100ms这个窗格就要删掉，1000ms-1100ms添加到最后  ===> 相当于往前滑动了一个窗格
 *
 * @Date:2022/8/3 13:40
 * @Author:qs@1.com
 */
public class MySlidingTimeWindow {
    private static final int SAMPLE_COUNT = 10;                             // 这个滑动时间窗口分了10个窗格
    private static final int WINDOW_INTERVAL = 1000;                  // 滑动时间窗口的间隔时间
    private static final int WINDOW_BUCKET_INTERVAL = 100;      // 滑动时间窗口中每个窗格的间隔时间
    private static final int LIMIT_COUNT = 100;                                // 限流阈值
    // 限流标记
    private static AtomicBoolean limit_flag = new AtomicBoolean(false);

    private LinkedList<Long> slots = new LinkedList<>();              // 滑动时间窗口的数据结构 - 用来存放滑动时间窗口的10个时间窗格
    private Long counter = 0L;                                                           // 计数器(每个窗格都有一个counter)，请求次数，可以放在redis中用于分布式系统的请求计数

    public void doCheck() throws InterruptedException {
        while (true) {
            // 滑动时间窗口构建
            slots.addLast(counter);
            if (slots.size() > SAMPLE_COUNT) {
                // 向前滑动一个时间窗格
                slots.removeFirst();
            }
            // 比较最后一个和第一个，两者相差100以上就限流
            if (slots.peekLast() - slots.peekFirst() > LIMIT_COUNT) {
                // 限流
                System.out.println("限流了 ===================> lastCounter=" + slots.peekLast() + " , firstCounter=" + slots.peekFirst());
                limit_flag.compareAndSet(false, true);
            } else {
                // 不限流，pass
                System.out.println("pass =====> size=" + slots.size() + " lastCounter=" + slots.peekLast() + " , firstCounter=" + slots.peekFirst());
                limit_flag.compareAndSet(true, false);
            }

            TimeUnit.MILLISECONDS.sleep(WINDOW_BUCKET_INTERVAL);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        MySlidingTimeWindow mySlidingTimeWindow = new MySlidingTimeWindow();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mySlidingTimeWindow.doCheck();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        // 主线程 -> 模拟请求访问
        while (true) {
            if (limit_flag.get()) {
                // 限流，请求丢弃
                continue;
            }

            // 不限流 +1，sleep来模拟请求时间间隔
            mySlidingTimeWindow.counter++;
            Thread.sleep(new Random().nextInt(17));
        }
    }

}
