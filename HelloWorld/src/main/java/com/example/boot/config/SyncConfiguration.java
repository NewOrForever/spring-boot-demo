package com.example.boot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * ClassName:SyncConfiguration
 * Package:com.example.boot.config
 * Description:
 *
 * @Date:2021/8/11 9:10
 * @Author:qs@1.com
 */
@Configuration
@EnableAsync
public class SyncConfiguration {
    @Bean(name = "scorePoolTaskExecutor")
    public ThreadPoolTaskExecutor getScorePoolTaskExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = getThreadPoolTaskExecutor("score-1");
        return threadPoolTaskExecutor;
    }


    @Bean(name = "redisRefreshPoolTaskExecutor")
    public ThreadPoolTaskExecutor getRedisRefreshPoolTaskExecutor() {
        return getThreadPoolTaskExecutor("redis-");
    }

    @Bean(name = "appPushPoolTaskExecutor")
    public ThreadPoolTaskExecutor getAppPushPoolTaskExecutor() {
        return getThreadPoolTaskExecutor("push-");
    }

    /**
     * 线程池通用方法
     * @param threadNamePrefix
     * @return
     */
    private ThreadPoolTaskExecutor getThreadPoolTaskExecutor(String threadNamePrefix) {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        // 核心线程数
        threadPoolTaskExecutor.setCorePoolSize(10);
        // 线程池维护线程的最大数量，只有在缓冲队列满了之后才会申请超过核心线程数的线程
        threadPoolTaskExecutor.setMaxPoolSize(100);
        // 缓存队列
        threadPoolTaskExecutor.setQueueCapacity(50);
        // 空闲时间，当超过了核心线程数之外的线程空闲时间到达之后会被销毁
        threadPoolTaskExecutor.setKeepAliveSeconds(200);
        // 异步方法内部线程名称
        threadPoolTaskExecutor.setThreadNamePrefix(threadNamePrefix);
        /*
        *   当线程池的任务缓存队列已满并且线程池中的线程数目达到maximumPoolSize，如果还有任务到来就会采取任务拒绝策略
        *   通常有以下四种策略：
        *   ThreadPlloExecutor.AbortPolicy：丢弃任务并抛出RejectedExecutionException异常。
        *   ThreadPlloExecutor.DiscardPolicy：也是丢弃任务，但是不抛出异常。
        *   ThreadPlloExecutor.DiscardOldestPolicy：丢弃队列最前面的任务，然后重新尝试执行任务（重复此过程）。
        *   ThreadPlloExecutor.CallerRunsPolicy：重试添加当前的任务，自动重复调用execute()方法，直到成功。
        * */
        threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }

}
