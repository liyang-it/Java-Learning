package com.example.rabbitmqproducer.controller;

import com.alibaba.fastjson2.JSONObject;
import com.example.rabbitmqproducer.exchange.TopicRabbitConfig;
import com.example.rabbitmqproducer.exchange.TopicToFanoutRabbitConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * <h2>主题型交换机发送消息控制层</h2>
 * <p>
 * <a href=https://docs.spring.io/spring-amqp/docs/current/reference/html/#sending-messages>发送消息官方文档</a>
 * </p>
 *
 * @author Evan <1922802352@qq.com>
 * @since 2023年07月03日 14:45
 */
@RestController
@RequestMapping(value = "/topic")
@Api(tags = "主题型交换机发送消息控制层")
@Slf4j
public class TopicExChangeMessageController {
	
	private final RabbitTemplate template;
	
	public TopicExChangeMessageController(RabbitTemplate template) {
		this.template = template;
	}
	
	@ApiOperation(value = "将消息发送到 topicExchange 交换机的 topic.man 路由对应队列中", notes = "发送消息")
	@GetMapping(value = "/sendMan/{message}")
	public String sendMan(@PathVariable(value = "message") String message) {
		
		String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		
		JSONObject json = new JSONObject();
		json.put("messageData", message);
		json.put("messageId", UUID.randomUUID().toString());
		json.put("createTime", createTime);
		
		template.convertAndSend("topicExchange", TopicRabbitConfig.MAN, json);
		log.info("消息发送成功");
		return message;
	}
	
	@ApiOperation(value = "将消息发送到 topicExchange 交换机的 topic.woman 路由对应队列中", notes = "发送消息")
	@GetMapping(value = "/sendWoMan/{message}")
	public String sendWoMan(@PathVariable(value = "message") String message) {
		
		String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		
		JSONObject json = new JSONObject();
		json.put("messageData", message);
		json.put("messageId", UUID.randomUUID().toString());
		json.put("createTime", createTime);
		
		template.convertAndSend("topicExchange", TopicRabbitConfig.WO_MAN, json);
		log.info("消息发送成功");
		return message;
	}
	
	
	@ApiOperation(value = "将消息发送到 topicExchange 交换机的 order.test 路由对应队列中", notes = "发送消息")
	@GetMapping(value = "/sendTest/{message}")
	public String sendTest(@PathVariable(value = "message") String message) {
		
		String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		
		JSONObject json = new JSONObject();
		json.put("messageData", message);
		json.put("messageId", UUID.randomUUID().toString());
		json.put("createTime", createTime);
		
		template.convertAndSend("topicExchange", TopicRabbitConfig.ORDER_TEST, json);
		log.info("消息发送成功");
		return message;
	}
	
	@ApiOperation(value = "使用 topic主题交换机实现 发布订阅模式交换机功能", notes = "发送消息")
	@GetMapping(value = "/sendToFanout/{message}")
	public String sendToFanout(@PathVariable(value = "message") String message) {
		
		JSONObject json = new JSONObject();
		json.put("messageData", message);
		json.put("messageId", UUID.randomUUID().toString());
		json.put("createTime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		
		template.convertAndSend(TopicToFanoutRabbitConfig.TOPIC_FANOUT_EXCHANGE, TopicToFanoutRabbitConfig.TOPIC_FANOUT_DOG_QUEUE, json);
		log.info("消息发送成功");
		return message;
	}
	
	
}
