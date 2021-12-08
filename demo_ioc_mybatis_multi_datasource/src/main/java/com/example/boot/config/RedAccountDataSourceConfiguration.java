package com.example.boot.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import tk.mybatis.spring.annotation.MapperScan;

import javax.sql.DataSource;

/**
 * ClassName:RedAccoutnDataSourceConfiguration
 * Package:com.example.boot.config
 * Description:
 *
 * @Date:2021/12/3 13:59
 * @Author:qs@1.com
 */
@Configuration
@MapperScan(basePackages = "com.example.boot.mapper.redAccount.mapper", sqlSessionFactoryRef = "redAccountSqlSessionFactory")
public class RedAccountDataSourceConfiguration {
    @Autowired
    @Qualifier("RedPacketDataSource")
    private DataSource redAccountDataSource;

    public static final String MAPPER_XML_LOCATION = "classpath*:com/example/boot/mapper/redAccount/mapper/*.xml";


    /**
     * 配置sqlsessionfactory
     */
    @Bean
    public SqlSessionFactory redAccountSqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(redAccountDataSource);
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
        return new SqlSessionTemplate(redAccountSqlSessionFactory());
    }

    /**
     * 配置事务
     * @return
     */
    @Bean
    public DataSourceTransactionManager transactionManager(){
        return new DataSourceTransactionManager(redAccountDataSource);
    }
}
