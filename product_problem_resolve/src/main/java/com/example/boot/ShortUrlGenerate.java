package com.example.boot;

import com.google.common.base.Charsets;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.util.MurmurHash;

/**
 * ClassName:ShortUrlGenerate
 * Package:com.example.boot
 * Description:
 *
 * @Date:2025/2/28 10:12
 * @Author:qs@1.com
 */
public class ShortUrlGenerate {

    public static void main(String[] args) {
        String longUrl = "http://192.168.50.67:9000/temp-test/2024/05/20/bbdef7ffc0984851a6f4a2a7cd3e13a2.jpg";

        String shortKey = generateShortUrl(longUrl);

        // shortUrl 与 longUrl 的映射关系存储到 Redis
        Jedis jedis = new Jedis("192.168.50.8", 6379);
        String key = "shortKey:" + shortKey;
        jedis.set(key, longUrl);
        jedis.expire(key, 3600 * 24);
        jedis.close();

        /**
         * 短链前缀（可以是专门用于短链的二级域名，也可以是自定义的短链前缀）
         * 例如：http://short.domain.com、http://www.domain.com/s/
         */
        String shortUrlPrefix = "http://localhost:8080/s/";
        // 拼接短链
        String shortUrl = shortUrlPrefix + shortKey;

        System.out.println("Short URL: " + shortUrl);
    }

    /**
     * 生成短链
     *
     * @param longUrl 原始长链接
     * @return 短链
     */
    public static String generateShortUrl(String longUrl) {
        // 获取 MurmurHash3_32 HashFunction (32位哈希)
        HashFunction hf32 = Hashing.murmur3_32_fixed();
        int hashValue32 = hf32.hashString(longUrl, Charsets.UTF_8).asInt();
        System.out.println("MurmurHash3_32: " + hashValue32);

        String shortKey = Base62Encoder.toBase62(hashValue32);
        System.out.println("Base62: " + shortKey);

        // 测试 Base62 解码
        long originHash32 = Base62Encoder.fromBase62(shortKey);
        System.out.println("Origin Hash32: " + originHash32);

        return shortKey;
    }
}

class Base62Encoder {
    private static final String BASE62_CHARS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int BASE = BASE62_CHARS.length();
    public static String toBase62(long uniqueId) {
        StringBuilder shortUrl = new StringBuilder();
        while (uniqueId > 0) {
            int remainder = (int) (uniqueId % BASE);
            shortUrl.append(BASE62_CHARS.charAt(remainder));
            uniqueId /= BASE;
        }
        return shortUrl.reverse().toString();
    }

    public static long fromBase62(String shortKey) {
        long uniqueId = 0;
        for (int i = 0; i < shortKey.length(); i++) {
            char c = shortKey.charAt(i);
            int digit = BASE62_CHARS.indexOf(c);
            if (digit == -1) {
                throw new IllegalArgumentException("Invalid character in short key: " + c);
            }
            uniqueId = uniqueId * BASE + digit;
        }
        return uniqueId;
    }
}
