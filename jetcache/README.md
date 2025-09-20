# 讲下几个 jetcache 的用法

## 自定义 valueEncoder 和 valueDecoder
这个就不多说了，参考下 `com.example.serialize.MyJsonSerialPolicy` 和 `com.example.serialize.FastJson2JsonRedisSerializer`

主要要注意配置文件中 valueEncoder 和 valueDecoder 的配置
1. 如果使用 jetcache 自带的则 valueEncoder 和 valueDecoder 值为 java/kryo/kryo5，参考源码`com.alicp.jetcache.anno.support.DefaultEncoderParser.parseDecoder`
2. 如果是自定义的话就需要先把自定义个 SerialPolicy 注册为 spring bean，然后配置文件中 valueEncoder 和 valueDecoder 的值为 bean:beanName
（例如：bean:fastJson2JsonRedisSerializer） ，源码见 `ccom.alicp.jetcache.anno.support.DefaultSpringEncoderParser.parseBeanName`
3. 自定义的 valueEncoder 和 valueDecoder 需要实现 `Function<Object, byte[]>` 或 `SerialPolicy` 接口，但一般我们实现 `SerialPolicy` 接口用的比较多
4. 存入外部缓存时，value 都会被包装到 `CacheValueHolder`，所以存入 redis 的实际就是个 `CacheValueHolder` 对象

## 讲一个坑
```java
 @PostConstruct
public void init() {
    QuickConfig quickConfig = QuickConfig.newBuilder(":user:cache:id:")
            .cacheType(CacheType.REMOTE)
            .expire(Duration.ofSeconds(120))
            .refreshPolicy(RefreshPolicy.newPolicy(10, TimeUnit.SECONDS))
            .loader(this::getUser)
            .penetrationProtect(true)
            .build();
    cacheManager.getOrCreateCache(quickConfig);
}

@Cached(name = ":user:cache:id:", key = "#id", expire = 100, cacheType = CacheType.BOTH)
// @CacheRefresh(refresh = 10, timeUnit = TimeUnit.SECONDS)
public User getUser(Long id) {
    System.out.println("-----> 未使用缓存");

    User user = new User();
    user.setId(id);
    user.setName("张三");
    user.setAge(18);
    return user;
}
```
上述方法中 `:user:cache:id:` 这个 Cache 实例的 `expire` 和 `cacheType` 属性值应该是多少？

答：`expire`: 120，`cacheType`: REMOTE，为什么？

这里看着像 @Cache 注解的是局部配置会覆盖全局配置，但实际上不是这样的，同一个 cacheName 的 Cache 实例只会创建一次，后续再创建
同名 Cache 实例时会直接返回之前创建的实例，`PostConstruct` 注解的方法是在 bean 初始化时执行， @Cached 注解的解析是 AOP BeanPostProcessor 的初始化后方法
中执行，所以 Cache 实例是在 `init` 方法中先创建的，`@Cached` 解析时不会再创建新的 Cache 实例（直接取 init 中创建的 Cache 实例），所以 `expire` 和 `cacheType` 属性值是 `init` 方法中配置的值

顺便说下如果 `init` 中 Cache 实例创建时没有设置 refreshPolicy，那么和 @Cached 注解的方法即使配置了 @CacheRefresh 注解，也不会生效定时刷新任务

## jetcache 作者关于 BOTH 和 syncLocal 的一些观点
@see https://github.com/alibaba/jetcache/issues/972

首先，syncLocal只适用于cacheType=BOTH类型的缓存，那就要先看看你为什么要设置为BOTH：只有qps很高且命中率很高的Cache实例，才需要设置为两级缓存（BOTH），在本地local cache内先查找就是为了给redis挡一下，降低一点redis的负担。如果qps很低，没必要设置BOTH（不要过早、过度优化）；如果QPS很高而命中率很低，说明有很多不同key，这种情况下也不适合BOTH，因为local jvm根本就装不下那么多，localLimit多了会降低JVM的gc性能，少了local命中率上不去，也没意思。

在qps和命中率双高的情况下，cacheType设置为BOTH，这时有一个问题，一个JVM对缓存进行了更新操作（这个更新包括PUT和REMOVE），它自己的local JVM和redis里面的数据更新了，可是其它JVM的local cache没更新，所以就需要一个机制来让其它JVM中local cache失效。

以前很多人要这个功能，当时只提供了一个localExpire的属性，让localExpire的超时时间短一点，超时后local get miss，然后去redis里面get，再刷新local。但这个办法有时候确实难以让人满意，才开发了syncLocal。

所以正确的做法是：

一切走默认，就用cacheType=remote
觉得redis负担大了，根据80%/20%的原则，找到qps和命中率双高的几个地方，改成BOTH，别的地方都不用管
改成BOTH以后如果不能接受local cache没更新，设置localExpire短一点，让local cache尽快失效，保证最终一致
不能接受localExpire方案的，再开syncLocal
可见要开syncLocal就是极少数情况。不能有什么功能就用什么功能，只看好处不看代价，然后觉得不合适，那不是当然的么，本来默认值才是最合适的。