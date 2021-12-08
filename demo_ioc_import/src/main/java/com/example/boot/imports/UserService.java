package com.example.boot.imports;

import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

/**
 * ClassName:UserService
 * Package:com.example.boot.imports
 * Description:
 *
 * @Date:2021/11/29 15:42
 * @Author:qs@1.com
 */
@Component
@Import(UserBean.class)
public class UserService {
}
