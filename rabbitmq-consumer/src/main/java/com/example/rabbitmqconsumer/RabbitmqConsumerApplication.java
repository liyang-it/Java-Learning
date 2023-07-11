package com.example.rabbitmqconsumer;

import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.rabbitmqconsumer", "cn.hutool.extra.spring"})
@Slf4j
@EnableRabbit
public class RabbitmqConsumerApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(RabbitmqConsumerApplication.class, args);
		Environment environment = SpringUtil.getBean(Environment.class);
		String port = environment.getProperty("server.port");
		String contentPath = environment.getProperty("server.servlet.context-path");
		log.info("RabbitMQ测试 - 消费者者客户端启动成功！！访问地址: http://127.0.0.1:{}{}", port, contentPath);
	}
	
}
