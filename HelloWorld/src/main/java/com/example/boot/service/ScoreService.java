package com.example.boot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * ClassName:ScoreService
 * Package:com.example.boot.service
 * Description:
 *
 * @Date:2021/8/11 9:10
 * @Author:qs@1.com
 */
@Service
@Slf4j
public class ScoreService {

    @Async
    public void addScore() {
        // TODO 模拟睡5s，用于赠送积分处理
        try {
            Thread.sleep(5 * 1000);
            log.info("------------积分处理------------");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 添加线程池的名称
    @Async("scorePoolTaskExecutor")
    public void addScore2() {
        // TODO 模拟睡5s，用于赠送积分处理
        try {
            Thread.sleep(5 * 1000);
            log.info("------------积分处理------------");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 添加线程池的名称
    @Async("scorePoolTaskExecutor")
    public void testEx() throws Exception {
        log.info("------------teset------------");
        throw new Exception("hahahahahhh");
    }
}
