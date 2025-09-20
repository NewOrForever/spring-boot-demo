package com.example;

import com.alicp.jetcache.CacheValueHolder;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import com.example.serialize.MyJsonSerialPolicy;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

/**
 * ClassName:JetcacheDemoApplication
 * Package:com.example
 * Description:
 *
 * @Date:2025/9/17 16:33
 * @Author:qs@1.com
 */
@SpringBootApplication
@EnableMethodCache(basePackages = "com.example")
public class JetcacheDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(JetcacheDemoApplication.class, args);
    }

}
