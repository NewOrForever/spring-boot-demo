package springboot;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

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
    @Cacheable(value = "userCache", key = "#id")
    public User getUser(Long id) {
        User user = new User();
        user.setId(id);
        user.setName("张三");
        user.setAge(18);
        return user;
    }

    @CachePut(value = "userCache", key = "#user.id")
    public void save(User user) {
        System.out.println("保存用户信息：" + user);
    }

    @CacheEvict(value = "userCache", key = "#id")
    public void delete(Long id) {
        System.out.println("删除用户信息，id：" + id);
    }
}
