package com.example.elasticsearch;

import com.example.elasticsearch.util.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.dromara.easyes.starter.register.EsMapperScan;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * SpringBoot启动类入口
 *
 * @author ifi-liyang
 */
@SpringBootApplication
@EsMapperScan("com.example.elasticsearch.mapper.es")
@MapperScan(basePackages = {"com.baomidou.mybatisplus.samples.quickstart.mapper", "com.example.elasticsearch.mapper"})
@Slf4j
@EnableConfigurationProperties
@ConfigurationPropertiesScan(basePackages = "com.example.elasticsearch.config")
@EnableAsync
@EnableTransactionManagement
public class ElasticsearchApplication {
	
	public static void main(String[] args) {
		
		SpringApplication.run(ElasticsearchApplication.class, args);
		Environment environment = BeanUtil.getBean(Environment.class);
		String port = environment.getProperty("server.port");
		String contentPath = environment.getProperty("server.servlet.context-path");
		
		log.info("Elastic Search示例 启动成功！！访问地址: http://127.0.0.1:{}{}", port, contentPath);
	}
	
}
