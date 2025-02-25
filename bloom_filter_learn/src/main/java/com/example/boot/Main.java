package com.example.boot;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import org.redisson.Redisson;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

/**
 * ClassName:LoggingExample
 * Package:com.example.boot
 * Description:
 *
 * @Date:2025/2/24 15:08
 * @Author:qs@1.com
 */
public class Main {
    public static void main(String[] args) {
        codeWithTongYiForBloomFilterTest();
        codeWithCopilotForBloomFilterTest();
        redisBloomFilterTest();
    }

    private static void codeWithTongYiForBloomFilterTest() {
        // 创建一个布隆过滤器，预计插入1000个元素，误判率为1%
        BloomFilter<Integer> bloomFilterWithTongYi = BloomFilter.create(Funnels.integerFunnel(), 1000, 0.01);

        // 添加元素到布隆过滤器
        bloomFilterWithTongYi.put(1);
        bloomFilterWithTongYi.put(2);
        bloomFilterWithTongYi.put(3);

        // 检查元素是否可能存在于布隆过滤器中
        System.out.println("Contains 1: " + bloomFilterWithTongYi.mightContain(1)); // true
        System.out.println("Contains 4: " + bloomFilterWithTongYi.mightContain(4)); // false
    }

        private static void redisBloomFilterTest() {
            Config config = new Config();
            config.useSingleServer().setAddress("redis://192.168.50.8:6379").setDatabase(0);
            RedissonClient redisson = Redisson.create(config);

            RBloomFilter<Object> bloomFilter = redisson.getBloomFilter("nameList");
            // 初始化布隆过滤器：预计元素为100000000L，误差率为3%，根据这两个参数会计算出底层的bit数组大小
            bloomFilter.tryInit(100000000L, 0.03);
            // 向布隆过滤器插入数据
            bloomFilter.add("zhuge");
            bloomFilter.add("tuling");

            System.out.println("Contains guojia: " + bloomFilter.contains("guojia"));
            System.out.println("Contains baiqi: " + bloomFilter.contains("baiqi"));
            System.out.println("Contains zhuge: " + bloomFilter.contains("zhuge"));
        }

    private static void codeWithCopilotForBloomFilterTest() {
        // 创建一个布隆过滤器，预计插入1000个元素，误报率为0.01
        BloomFilter<Integer> bloomFilterWithCopilot = BloomFilter.create(Funnels.integerFunnel(), 1000, 0.01);

        // 向布隆过滤器中添加元素
        for (int i = 0; i < 1000; i++) {
            bloomFilterWithCopilot.put(i);
        }

        // 检查元素是否存在于布隆过滤器中
        System.out.println("Contains 500？" + bloomFilterWithCopilot.mightContain(500)); // true
        System.out.println("Contains 1001？" + bloomFilterWithCopilot.mightContain(1001)); // false
    }
}
