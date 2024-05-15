package com.example.boot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * ClassName:MinioProperties
 * Package:com.example.boot.config
 * Description:
 *
 * @Date:2024/4/16 10:56
 * @Author:qs@1.com
 */
@Data
@ConfigurationProperties(prefix = MinioProperties.PREFIX)
public class MinioProperties {

    /**
     * 配置前缀
     */
    public static final String PREFIX = "oss";

    /**
     * 对象存储名称
     */
    private String name;

    /**
     * 对象存储服务的URL
     */
    private String endpoint;

    /**
     * Access key 账户ID
     */
    private String accessKey;

    /**
     * Secret key 密码
     */
    private String secretKey;
}
