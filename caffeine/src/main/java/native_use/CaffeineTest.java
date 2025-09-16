package native_use;

import com.github.benmanes.caffeine.cache.*;

import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * ClassName:CaffeineTest
 * Package:native_use
 * Description: 本地缓存 caffeine 原生使用测试
 *
 * @Date:2025/9/15 14:08
 * @Author:qs@1.com
 */
public class CaffeineTest {
    public static void main(String[] args) {
        // 手动创建缓存测试
        // manualCreateTest();

        // Loading Cache 测试
        // loadingCacheTest();

        // async cache 异步缓存测试
        // asyncCacheTest();

        // 淘汰测试
        // evictionTest();

        // 刷新测试
        // refreshTest();

        // 统计测试
        statsTest();
    }

    private static void statsTest() {
        LoadingCache<String, String> cache = Caffeine.newBuilder()
                .refreshAfterWrite(1, TimeUnit.SECONDS)
                .expireAfterWrite(1, TimeUnit.SECONDS)
                .expireAfterAccess(1, TimeUnit.SECONDS)
                .recordStats()
                .build(key -> {
                    Thread.sleep(1000);
                    return new Date().toString();
                });

        cache.put("key1", "value1");
        cache.getIfPresent("key1");

        System.out.println(cache.stats());
    }

    private static int NUM = 0;

    /**
     * refreshAfterWrite 仅支持 LoadingCache
     */
    private static void refreshTest() {
        LoadingCache<Integer, Integer> cache = Caffeine.newBuilder()
                // 创建/更新缓存后经过指定时间间隔刷新缓存，仅支持LoadingCache
                .refreshAfterWrite(1, TimeUnit.SECONDS)
                .build(key -> ++NUM);
        System.out.println(cache.getIfPresent(1));
        // 缓存不存在则调用load方法加载数据
        System.out.println(cache.get(1));
        // 缓存不存在则返回 null
        System.out.println(cache.getIfPresent(1));

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // 为什么超过 refresh 后第一次获取会返回旧值呢？
        // 因为第一次获取时，发现缓存过期了，然后才会去刷新缓存
        // 所以第一次获取时，还是会返回旧值
        // 第二次获取时，缓存已经刷新完毕了，就会返回新值
        System.out.println(cache.getIfPresent(1));
        System.out.println(cache.getIfPresent(1));
    }

    /**
     * 缓存淘汰：
     * 1. 缓存大小超出 maximumSize
     * 2. 缓存权重超出 maximumWeight
     * 3. 超时过期 expireAfterWrite/expireAfterAccess
     * <p>
     * 超时过期这里不演示了，这里只演示了缓存大小超出 maximumSize及权重超出 maximumWeight
     * <p>
     * maximumSize 和 maximumWeight 不能同时使用
     */
    private static void evictionTest() {
        System.out.println("----------------缓存大小淘汰------------------");
        Cache<Integer, Integer> cache = Caffeine.newBuilder()
                // 超过10个后就会使用 TinyLfu 算法淘汰缓存
                .maximumSize(10)
                .evictionListener((key, value, cause) -> {
                    System.out.println("Key: " + key + " value: " + value + " was removed because " + cause);
                })
                .build();
        for (int i = 1; i <= 20; i++) {
            cache.put(i, i);
        }

        try {
            // 为什么要等待呢？
            // 因为缓存淘汰是异步的
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(cache.asMap());

        System.out.println("----------------缓存权重淘汰------------------");
        Cache<Integer, Integer> weightCache = Caffeine.newBuilder()
                // 超过100权重后就会使用 TinyLfu 算法淘汰缓存
                .maximumWeight(100)
                // 每个缓存项的权重，这里我们简单地使用 value 作为权重
                .weigher((Weigher<Integer, Integer>) (key, value) -> value)
                .evictionListener((key, value, cause) -> {
                    System.out.println("Key: " + key + " value: " + value + " was removed because " + cause);
                })
                .build();
        for (int i = 0; i < 20; i++) {
            weightCache.put(i, i);
        }
        try {
            // 为什么要等待呢？
            // 因为缓存淘汰是异步的
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(weightCache.asMap());
    }

    private static void asyncCacheTest() {
        AsyncLoadingCache<String, String> asyncLoadingCache = Caffeine.newBuilder()
                // 创建/更新缓存后经过指定时间间隔刷新缓存，仅支持LoadingCache
                .refreshAfterWrite(1, TimeUnit.SECONDS)
                .expireAfterWrite(1, TimeUnit.SECONDS)
                .expireAfterAccess(1, TimeUnit.SECONDS)
                .maximumSize(10)
                .buildAsync(key -> {
                    Thread.sleep(1000);
                    return new Date().toString();
                });

        CompletableFuture<String> future = asyncLoadingCache.get("key");
        future.thenAccept(value -> {
            System.out.println("key: " + value);
        });

        future.join();
    }

    private static void loadingCacheTest() {
        LoadingCache<String, String> loadingCache = Caffeine.newBuilder()
                // 创建/更新缓存后经过指定时间间隔刷新缓存，仅支持LoadingCache
                .refreshAfterWrite(1, TimeUnit.SECONDS)
                .expireAfterWrite(1, TimeUnit.SECONDS)
                .expireAfterAccess(1, TimeUnit.SECONDS)
                .maximumSize(10)
                .build(key -> new Date().toString());

        for (int i = 0; i < 10; i++) {
            System.out.println(loadingCache.get("key"));
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * removalListener 缓存移除监听器
     * 1. 触发时机：当缓存项以任何方式被移除时都会触发
     * 2. 触发原因：包括所有 RemovalCause，如：
     * EXPLICIT（显式删除，如调用 invalidate()）
     * SIZE（因容量限制被驱逐）
     * EXPIRED（因过期被移除）
     * COLLECTED（被垃圾回收）
     * REPLACED（被替换）
     * <p>
     * evictionListener 缓存移除监听器
     * 1. 触发时机：仅在缓存项因缓存策略被自动驱逐时触发
     * 2. 触发原因：只针对 RemovalCause.wasEvicted() 为 true 的情况：
     * SIZE（因容量限制被驱逐）
     * EXPIRED（因过期被驱逐）
     * COLLECTED（被垃圾回收）
     */
    private static void manualCreateTest() {
        Cache<String, String> cache = Caffeine.newBuilder()
                .initialCapacity(10)
                .maximumSize(2)
                // expireAfterWrite 和 expireAfterAccess 同时配置时，以expireAfterWrite为准
                // 最后一次写入后经过指定时间后过期
                .expireAfterWrite(1, java.util.concurrent.TimeUnit.SECONDS)
                // 最后一次访问（读/写）后经过指定时间后过期
                .expireAfterAccess(1, java.util.concurrent.TimeUnit.SECONDS)
                .removalListener((key, value, cause) ->
                        System.out.println("Key " + key + " was removed because " + cause))
                .evictionListener((key, value, cause) -> {
                })
                // 开启统计功能
                .recordStats()
                .build();

        System.out.println("----------------添加缓存------------------");
        // 添加缓存
        cache.put("key1", "value1");
        cache.put("key2", "value2");

        // 获取缓存
        // 不阻塞，不存在时返回null
        String value1 = cache.getIfPresent("key1");
        System.out.println("key1: " + value1);
        // 阻塞式获取，key不存在时调用lambda表达式加载数据
        String value3 = cache.get("key3", key -> "value3");
        ;
        System.out.println("key3: " + value3);

        // 等待2秒钟，超过过期时间
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("----------------缓存过期------------------");

        System.out.println("cache size after expiration: " + cache.estimatedSize());
        System.out.println(cache.asMap());
        System.out.println("cache size after expiration: " + cache.estimatedSize());

        // 再次获取缓存，应该已经过期
        String expiredValue = cache.getIfPresent("key1");
        System.out.println("key1 after expiration: " + expiredValue);

        System.out.println("cache size after expiration: " + cache.estimatedSize());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
