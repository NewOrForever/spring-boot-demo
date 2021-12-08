package sq.core.factories;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ClassName:UserConfiguration
 * Package:sq.core.factories
 * Description:
 *
 * @Date:2021/11/30 15:20
 * @Author:qs@1.com
 */
@Configuration
public class UserConfiguration {
    @Bean
    public UserModel userModel() {
        return new UserModel();
    }
}
