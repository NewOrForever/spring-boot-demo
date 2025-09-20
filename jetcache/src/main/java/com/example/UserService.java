package com.example;

import com.alicp.jetcache.CacheManager;
import com.alicp.jetcache.RefreshPolicy;
import com.alicp.jetcache.anno.*;
import com.alicp.jetcache.template.QuickConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * ClassName:UserService
 * Package:springboot
 * Description:
 *
 * @Date:2025/9/15 16:58
 * @Author:qs@1.com
 */
@Component
public class UserService {
    @Autowired
    private CacheManager cacheManager;

    /**
     * 我还是倾向于通过 API 的方式在 bean 初始化的时候创建 Cache 实例
     * 这样我可以把所有的缓存都放在一个 Configuration 类中一起管理
     */
    @PostConstruct
    public void init() {
        QuickConfig quickConfig = QuickConfig.newBuilder(":user:cache:id:")
                /**
                 * local：{@link com.alicp.jetcache.embedded.CaffeineCache}
                 * Remote：{@link com.alicp.jetcache.redisson.RedissonCache}
                 * BOTH: {@link com.alicp.jetcache.MultiLevelCache}
                 */
                // .cacheType(CacheType.REMOTE)
                .cacheType(CacheType.BOTH)
                .expire(Duration.ofSeconds(120))
                .refreshPolicy(RefreshPolicy.newPolicy(30, TimeUnit.SECONDS))
                .loader(this::getUser)
                .penetrationProtect(true)
                .build();
        cacheManager.getOrCreateCache(quickConfig);
    }

    /**
     * 既然选择使用 API 方式在 bean 初始化的时候创建 Cache 实例
     * 那么就要知道这里 @Cached 中的属性是不会生效的，用的还是 init 中创建的 Cache 配置
     * 这里只是为了演示下 @Cached 注解的一些属性
     */
    @Cached(name = ":user:cache:id:", key = "#id", expire = 120, cacheType = CacheType.REMOTE)
    // @CacheRefresh(refresh = 10, timeUnit = TimeUnit.SECONDS)
    public User getUser(Long id) {
        System.out.println("-----> 未使用缓存");

        User user = new User();
        user.setId(id);
        user.setName("张三");
        user.setAge(18);
        return user;
    }

    @CacheUpdate(name = ":user:cache:id:", key = "#user.id", value = "#user")
    public void update(User user) {
        System.out.println("保存用户信息：" + user);
    }

    @CacheInvalidate(name = ":user:cache:id:", key = "#id")
    public void delete(Long id) {
        System.out.println("删除用户信息，id：" + id);
    }
}
