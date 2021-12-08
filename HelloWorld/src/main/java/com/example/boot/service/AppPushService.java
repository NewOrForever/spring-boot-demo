package com.example.boot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * ClassName:AppPushService
 * Package:com.example.boot.service
 * Description:
 *
 * @Date:2021/8/11 14:41
 * @Author:qs@1.com
 */
@Service
@Slf4j
public class AppPushService {

    @Async("appPushPoolTaskExecutor")
    public void appPush() {
        try {
            Thread.sleep(5 * 1000);
            log.info("--------------app push---------------");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
