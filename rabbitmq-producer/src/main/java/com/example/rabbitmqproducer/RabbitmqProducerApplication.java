package com.example.rabbitmqproducer;

import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

/**
 * @author ifi-liyang
 *
 */
@SpringBootApplication
@ComponentScan(basePackages={"cn.hutool.extra.spring", "com.example.rabbitmqproducer", "com.example.rabbitmqproducer.controller"})
@Slf4j
@EnableRabbit
public class RabbitmqProducerApplication {
	
	public static void main(String[] args) {
		
		SpringApplication.run(RabbitmqProducerApplication.class, args);
		Environment environment = SpringUtil.getBean(Environment.class);
		String port = environment.getProperty("server.port");
		String contentPath = environment.getProperty("server.servlet.context-path");
		
		log.info("RabbitMQ测试 - 生产者客户端启动成功！！访问地址: http://127.0.0.1:{}{}", port, contentPath);
		log.info("如果出现客户端启动成功，但是对应交换机、队列没有自动创建，请检查RabbitMQ是否已安装延迟插件并且启动延迟插件");
		log.info("RabbitMQ 延迟插件下载地址：https://github.com/rabbitmq/rabbitmq-delayed-message-exchange");
		
	}
	
}
