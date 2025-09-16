package springboot;

/**
 * ClassName:CacheEnum
 * Package:springboot
 * Description:
 *
 * @Date:2025/9/15 16:45
 * @Author:qs@1.com
 */
public enum CacheEnum {
    /**
     * 商品缓存
     */
    GOODS("goodsCache", 10, 10, 50),
    /**
     * 订单缓存
     */
    ORDER("orderCache", 10, 10, 50),
    /**
     * 用户缓存
     */
    USER("userCache", 10, 10, 50);


    private final String name;
    private final int expireSeconds;
    private final int initCapacity;
    private final int maxSize;

    CacheEnum(String name, int expireSeconds, int initCapacity, int maxSize) {
        this.name = name;
        this.expireSeconds = expireSeconds;
        this.initCapacity = initCapacity;
        this.maxSize = maxSize;
    }

    public int getInitCapacity() {
        return initCapacity;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public String getName() {
        return name;
    }

    public int getExpireSeconds() {
        return expireSeconds;
    }
}
