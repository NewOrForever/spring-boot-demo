package com.example.boot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * ClassName:RedisService
 * Package:com.example.boot.service
 * Description:
 *
 * @Date:2021/8/11 14:18
 * @Author:qs@1.com
 */
@Service
@Slf4j
public class RedisService {

    @Async("redisRefreshPoolTaskExecutor")
    public void refreshRedis() {
        try {
            Thread.sleep(5 * 1000);
            log.info("---------------------refresh redis----------------------");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
