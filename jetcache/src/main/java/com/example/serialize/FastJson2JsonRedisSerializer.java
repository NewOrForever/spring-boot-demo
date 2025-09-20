package com.example.serialize;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.JSONWriter;
import com.alicp.jetcache.anno.SerialPolicy;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.function.Function;

/**
 * Redis使用FastJson序列化
 */
@Component
public class FastJson2JsonRedisSerializer implements SerialPolicy {
    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    public byte[] serialize(Object t) throws SerializationException {
        if (t == null) {
            return new byte[0];
        }
        return JSON.toJSONString(t, JSONWriter.Feature.WriteClassName).getBytes(DEFAULT_CHARSET);
    }

    public Object deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null || bytes.length <= 0) {
            return null;
        }
        String str = new String(bytes, DEFAULT_CHARSET);

        return JSON.parseObject(str, Object.class, JSONReader.Feature.SupportAutoType);
    }

    @Override
    public Function<Object, byte[]> encoder() {
        return t -> serialize(t);
    }

    @Override
    public Function<byte[], Object> decoder() {
        return bytes -> deserialize(bytes);
    }
}
