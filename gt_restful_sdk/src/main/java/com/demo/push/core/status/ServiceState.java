package com.demo.push.core.status;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * ClassName:ServiceState
 * Package:com.demo.push.core.status
 * Description:
 *
 * @Date:2023/4/4 17:43
 * @Author:qs@1.com
 */
public class ServiceState {
    private AtomicInteger callTimes = new AtomicInteger(0);
    private AtomicInteger failedTimes = new AtomicInteger(0);
    private AtomicLong callAllTime = new AtomicLong(0);

    public void incrCallTimes() {
        this.callTimes.incrementAndGet();
    }

    public void addCallTime(long millis) {
        this.callAllTime.addAndGet(millis);
    }

    public void incrFailedTimes() {
        this.failedTimes.incrementAndGet();
    }

    public AtomicInteger getCallTimes() {
        return callTimes;
    }

    public void setCallTimes(AtomicInteger callTimes) {
        this.callTimes = callTimes;
    }

    public AtomicInteger getFailedTimes() {
        return failedTimes;
    }

    public void setFailedTimes(AtomicInteger failedTimes) {
        this.failedTimes = failedTimes;
    }

    public AtomicLong getCallAllTime() {
        return callAllTime;
    }

    public void setCallAllTime(AtomicLong callAllTime) {
        this.callAllTime = callAllTime;
    }
}
