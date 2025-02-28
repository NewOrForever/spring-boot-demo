package com.example.boot;

import redis.clients.jedis.Jedis;

import static java.lang.Thread.sleep;

/**
 * ClassName:SlidingWindowRateLimiter
 * Package:com.example.boot
 * Description:
 *
 * @Date:2025/2/26 13:56
 * @Author:qs@1.com
 */
public class SlidingWindowRateLimiter {
    private Jedis jedis;
    private String key;
    private int limit = 3; // 限制请求次数最大3次
    private int lockTime;  // 锁定用户的时间，单位：秒

    public SlidingWindowRateLimiter(Jedis jedis, String key, int limit, int lockTime) {
        this.jedis = jedis;
        this.key = key; // 接口path:userId
        this.limit = limit;
        this.lockTime = lockTime;  // 锁定时间
    }

    public boolean allowRequest(String userId) {
        // 当前时间戳，单位：毫秒
        long currentTime = System.currentTimeMillis();

        // 锁定键的名称（锁定的用户）
        String lockKey = "lock:" + userId;

        // 检查用户是否已被锁定
        if (jedis.exists(lockKey)) {
            System.out.println("用户已被锁定");
            return false;  // 用户已被锁定，返回 false
        }

        // 使用Lua脚本来确保原子性操作
        String luaScript = "local window_start = ARGV[1] - 5000\n" + // 计算5秒的起始时间，5分钟的话就是300000
                "redis.call('ZREMRANGEBYSCORE', KEYS[1], '-inf', window_start)\n" +  // 清理过期的请求
                "local current_requests = redis.call('ZCARD', KEYS[1])\n" +  // 获取当前请求次数
                "if current_requests < tonumber(ARGV[2]) then\n" +  // 如果请求次数小于限制
                "    redis.call('ZADD', KEYS[1], ARGV[1], ARGV[1])\n" +  // 添加当前请求时间
                "    return 1\n" +  // 允许请求
                "else\n" +
                "    redis.call('SET', KEYS[2], 1, 'EX', tonumber(ARGV[3]))\n" +  // 锁定用户
                "    return 0\n" +  // 拒绝请求
                "end";

        // 调用 Lua 脚本进行原子操作
        Object result = jedis.eval(luaScript, 2, key, lockKey, String.valueOf(currentTime), String.valueOf(limit), String.valueOf(lockTime));

        // 返回操作结果
        return (Long) result == 1;
    }

    public static void main(String[] args) throws InterruptedException {
        Jedis jedis = new Jedis("192.168.50.8", 6379);
        String userId = "10086";
        SlidingWindowRateLimiter rateLimiter = new SlidingWindowRateLimiter(jedis, "/user/login:" + userId, 3, 3);
        for (int i = 0; i < 10; i++) {
            sleep(1000);
            System.out.println(rateLimiter.allowRequest(userId));
        }
        jedis.close();
    }

}
