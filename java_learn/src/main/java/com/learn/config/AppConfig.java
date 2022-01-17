package com.learn.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

/**
 * ClassName:appConfig
 * Package:com.learn.config
 * Description:
 *
 * @Date:2021/12/20 14:44
 * @Author:qs@1.com
 */
@EnableAspectJAutoProxy
@ComponentScan(basePackages = "com.learn")
public class AppConfig {
}
