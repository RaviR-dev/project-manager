package com.cts.fullstack.assignment;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

@Configuration
@Profile("main")
public class DatabaseConfig {

	@Autowired
	private Environment env;
	

	@Primary
    @Bean
    public DataSource getDataSource()
    {
        try
        {
        	final DataSourceProperties mysqlDataSourceProperties = new DataSourceProperties();
            mysqlDataSourceProperties.setUrl(env.getProperty("spring.mysql.datasource.url"));
            mysqlDataSourceProperties.setDriverClassName(env.getProperty("spring.mysql.datasource.driver-class-name"));
            mysqlDataSourceProperties.setUsername(env.getProperty("spring.mysql.datasource.username"));
            mysqlDataSourceProperties.setPassword(env.getProperty("spring.mysql.datasource.password"));
        	DataSource mysqlDS = mysqlDataSourceProperties.initializeDataSourceBuilder().build();
        	mysqlDS.getConnection();
            return mysqlDS;
        } 
        catch (Exception e) 
        {
        	final DataSourceProperties h2DataSourceProperties = new DataSourceProperties();
            h2DataSourceProperties.setUrl(env.getProperty("spring.h2.datasource.url"));
            h2DataSourceProperties.setDriverClassName(env.getProperty("spring.h2.datasource.driver-class-name"));
            h2DataSourceProperties.setUsername(env.getProperty("spring.h2.datasource.username"));
            h2DataSourceProperties.setPassword(env.getProperty("spring.h2.datasource.password"));
        	DataSource h2DS = h2DataSourceProperties.initializeDataSourceBuilder().build();
            return h2DS;
        }
    }
	
}
