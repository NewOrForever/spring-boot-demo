package com.example.serialize;

import com.alicp.jetcache.CacheValueHolder;
import com.alicp.jetcache.anno.SerialPolicy;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import java.util.function.Function;

/**
 * ClassName:MyFastjson2ValueEncoder
 * Package:com.example
 * Description:
 *
 * @Date:2025/9/18 15:57
 * @Author:qs@1.com
 */
public class MyJsonSerialPolicy implements SerialPolicy {
    private Jackson2JsonRedisSerializer<CacheValueHolder> jackson2JsonRedisSerializer;

    public void setJackson2JsonRedisSerializer(Jackson2JsonRedisSerializer<CacheValueHolder> jackson2JsonRedisSerializer) {
        this.jackson2JsonRedisSerializer = jackson2JsonRedisSerializer;
    }

    @Override
    public Function<Object, byte[]> encoder() {
        return (value) -> jackson2JsonRedisSerializer.serialize(value);
    }

    @Override
    public Function<byte[], Object> decoder() {
        return bytes -> jackson2JsonRedisSerializer.deserialize(bytes);
    }

}
