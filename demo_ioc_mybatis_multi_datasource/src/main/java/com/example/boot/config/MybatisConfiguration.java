package com.example.boot.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * ClassName:MybatisConfiguration
 * Package:com.example.boot.config
 * Description:
 *
 * @Date:2021/12/3 11:15
 * @Author:qs@1.com
 */
@Configuration
@EnableConfigurationProperties
@EnableTransactionManagement(proxyTargetClass = true)
public class MybatisConfiguration {
    /**
     * account数据库配置前缀
     */
    final static String ACCOUNT_PREFIX = "spring.datasource.druid.account";
    /**
     * redpacket
     */
    final static String REDPACKET_PREFIX = "spring.datasource.druid.redpacket";

    /**
     * 配置Account数据库的数据源
     * @return
     */
    @Bean(name = "AccountDataSource")
    @ConfigurationProperties(prefix = ACCOUNT_PREFIX)
    public DataSource accountDataSource(){
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * 配置RedPacket数据库的数据源
     * @return
     */
    @Bean(name = "RedPacketDataSource")
    @ConfigurationProperties(prefix = REDPACKET_PREFIX)
    public DataSource redPacketDataSource(){
        return DruidDataSourceBuilder.create().build();
    }

}
