package com.example.config;

import com.alicp.jetcache.CacheValueHolder;
import com.example.serialize.MyJsonSerialPolicy;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

/**
 * ClassName:VauleParserConfig
 * Package:com.example.config
 * Description:
 *
 * @Date:2025/9/18 17:21
 * @Author:qs@1.com
 */
@Configuration
public class VauleParserConfig {

    @Bean(name = "myJsonSerialPolicy")
    public MyJsonSerialPolicy myJsonSerialPolicy() {
        Jackson2JsonRedisSerializer<CacheValueHolder> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(CacheValueHolder.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        MyJsonSerialPolicy serialPolicy = new MyJsonSerialPolicy();
        serialPolicy.setJackson2JsonRedisSerializer(jackson2JsonRedisSerializer);
        return serialPolicy;
    }
}
