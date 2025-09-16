package springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName:CacheTestController
 * Package:springboot
 * Description:
 *
 * @Date:2025/9/15 17:27
 * @Author:qs@1.com
 */
@RestController
@RequestMapping("/cache")
public class CacheTestController {
    @Autowired
    private UserService userService;
    @GetMapping("/getUser")
    public User getUser(Long id) {
        return userService.getUser(id);
    }
}
