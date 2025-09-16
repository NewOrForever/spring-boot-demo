package springboot;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator;
import org.springframework.aop.framework.autoproxy.InfrastructureAdvisorAutoProxyCreator;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurationSelector;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.annotation.ProxyCachingConfiguration;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.AutoProxyRegistrar;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * ClassName:CacheConfig
 * Package:springboot
 * Description:
 *
 * @Date:2025/9/15 16:41
 * @Author:qs@1.com
 *
 * 源码：
 * @see EnableCaching
 * @see CachingConfigurationSelector
 * @see AutoProxyRegistrar -> 注册 BeanPostProcessor {@link InfrastructureAdvisorAutoProxyCreator}
 * @see ProxyCachingConfiguration -> 注册 Advisor、PointCut、MethodInterceptor
 * 入口 bean 创建时执行 BeanPostProcessor：{@link AbstractAutoProxyCreator#postProcessAfterInitialization(Object, String)}
 *      - 如果时循环引用的 Bean 的话应该是提前 AOP 的：{@link AbstractAutoProxyCreator#getEarlyBeanReference(Object, String)}
 */
@Configuration
@EnableCaching // 启用 spring cache
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        List<CaffeineCache> caches = new ArrayList<>();
        // 定义多个 cache 实例：循环添加枚举类中自定义的缓存
        for (CacheEnum cacheEnum : CacheEnum.values()) {
            CaffeineCache cache = new CaffeineCache(cacheEnum.getName(),
                    Caffeine.newBuilder()
                            .initialCapacity(cacheEnum.getInitCapacity())
                            .maximumSize(cacheEnum.getMaxSize())
                            .expireAfterWrite(cacheEnum.getExpireSeconds(), TimeUnit.SECONDS)
                            .build());
            caches.add(cache);
        }

        cacheManager.setCaches(caches);
        return cacheManager;
    }
}
