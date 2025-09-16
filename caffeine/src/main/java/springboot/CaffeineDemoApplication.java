package springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * ClassName:CaffeineDemoApplication
 * Package:springboot
 * Description:
 * 1. 引入 caffeine依赖以及spring-boot-starter-cache依赖
 * 2. 创建缓存配置类 CacheConfig
 * 3. 在启动类或者配置类上添加 @EnableCaching 注解
 * 4. 在需要缓存的方法上添加 @Cacheable 等注解
 *
 * @Date:2025/9/15 16:38
 * @Author:qs@1.com
 */
@SpringBootApplication
public class CaffeineDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(CaffeineDemoApplication.class, args);
    }
}
