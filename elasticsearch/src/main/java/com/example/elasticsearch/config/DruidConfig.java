package com.example.elasticsearch.config;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;

/**
 * <h2>druid连接池</h2>
 * <p>
 *
 * </p>
 *
 * @author Evan <1922802352@qq.com>
 * @since 2023年07月13日 11:08
 */
@Configuration
@Slf4j
public class DruidConfig {
	@Bean
	public DruidDataSource druidDataSource() throws SQLException {
		DruidDataSource druidDataSource = new DruidDataSource();
		druidDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		druidDataSource.setUrl("jdbc:mysql://localhost:3306/elasticsearch-demo?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true&verifyServerCertificate=false&useSSL=false&rewriteBatchedStatements=true&zeroDateTimeBehavior=convertToNull");
		druidDataSource.setUsername("root");
		druidDataSource.setPassword("123456");
		druidDataSource.setFilters("stat");
		druidDataSource.setInitialSize(50);
		druidDataSource.setMaxActive(100);
		druidDataSource.setMaxWait(60000L);
		druidDataSource.setMinIdle(50);
		druidDataSource.setPoolPreparedStatements(false);
		druidDataSource.setMaxOpenPreparedStatements(20);
		druidDataSource.setValidationQuery("SELECT 1 FROM DUAL");
		druidDataSource.setTimeBetweenEvictionRunsMillis(60000L);
		druidDataSource.setMinEvictableIdleTimeMillis(300000L);
		druidDataSource.setTestWhileIdle(true);
		druidDataSource.setTestOnBorrow(false);
		druidDataSource.setTestOnReturn(false);
		druidDataSource.setAsyncInit(true);
		log.info("DruidDataSource连接池配置初始化成功");
		return druidDataSource;
	}
}
