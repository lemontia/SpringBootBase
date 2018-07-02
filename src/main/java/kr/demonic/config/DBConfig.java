package kr.demonic.config;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;


@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = "kr.demonic", annotationClass = Mapper.class)
public class DBConfig {

//    @Autowired
//    private DataSource datasource;

//    @Bean
//    public DataSourceTransactionManager txManager(){
//        return new DataSourceTransactionManager(datasource);
//    }
}
