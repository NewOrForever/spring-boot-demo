package com.example.boot.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import tk.mybatis.spring.annotation.MapperScan;

import javax.sql.DataSource;
import java.io.IOException;

/**
 * ClassName:AccountDataSourceConfiguration
 * Package:com.example.boot.config
 * Description:
 *
 * @Date:2021/12/3 13:36
 * @Author:qs@1.com
 */
@Configuration
@MapperScan(basePackages = "com.example.boot.mapper.account.mapper", sqlSessionFactoryRef = "accountSqlSessionFactory")
public class AccountDataSourceConfiguration {
    @Autowired
    @Qualifier("AccountDataSource")
    private DataSource accountDataSource;

    public static final String MAPPER_XML_LOCATION = "classpath*:com/example/boot/mapper/account/mapper/*.xml";


    /**
     * 配置sqlsessionfactory
     */
    @Bean
    public SqlSessionFactory accountSqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(accountDataSource);
        // 指定XML文件路径
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MAPPER_XML_LOCATION));
        return sqlSessionFactoryBean.getObject();
    }

    /**
     * 配置sqlsessiontemplate
     * @return
     * @throws Exception
     */
    @Bean
    public SqlSessionTemplate springSqlSessionTemplate() throws Exception {
        return new SqlSessionTemplate(accountSqlSessionFactory());
    }

    /**
     * 配置事务
     * @return
     */
    @Bean
    public DataSourceTransactionManager transactionManager(){
        return new DataSourceTransactionManager(accountDataSource);
    }
}
