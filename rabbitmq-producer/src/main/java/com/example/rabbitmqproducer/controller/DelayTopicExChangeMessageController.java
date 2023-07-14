package com.example.rabbitmqproducer.controller;

import com.alibaba.fastjson2.JSONObject;
import com.example.rabbitmqproducer.exchange.DelayRabbitConfig;
import com.example.rabbitmqproducer.exchange.DelayTopicRabbitConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * <h2>直连型交换机发送消息控制层</h2>
 * <p>
 * <a href=https://docs.spring.io/spring-amqp/docs/current/reference/html/#sending-messages>发送消息官方文档</a>
 * </p>
 *
 * @author Evan <1922802352@qq.com>
 * @since 2023年07月03日 14:45
 */
@RestController
@RequestMapping(value = "/topic/delay")
@Api(tags = "(延迟消息)topic主题交换机发送消息控制层")
@Slf4j
public class DelayTopicExChangeMessageController {
	
	private final RabbitTemplate template;
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	
	public DelayTopicExChangeMessageController(RabbitTemplate template) {
		this.template = template;
	}
	
	
	@ApiOperation(value = "发送消息, 使用topic实现直连交换机功能，同时使用死信交换机实现延迟消息", notes = "发送消息")
	@GetMapping(value = "/send/{message}")
	public String send(@PathVariable(value = "message") String message) {
		
		
		JSONObject json = new JSONObject();
		json.put("messageData", message);
		json.put("messageId", UUID.randomUUID().toString());
		json.put("createTime", formatter.format(LocalDateTime.now()));
		
		
		Message msg = MessageBuilder.withBody(json.toJSONString().getBytes(StandardCharsets.UTF_8))
				// 持久化消息
				.setDeliveryMode(MessageDeliveryMode.PERSISTENT)
				// 设置过期时间，永远都不要消费这个消息，等待过期进入死信队列才消费,设置 5 秒
				.setExpiration(String.valueOf(5 * 1000)).build();
		
		template.convertAndSend(DelayTopicRabbitConfig.TOPIC_TEST_EXCHANGE, DelayTopicRabbitConfig.TOPIC_TEST_QUEUE, msg);
		
		log.info("方法一：延迟消息发送成功,发送时间：{}", formatter.format(LocalDateTime.now()));
		return message;
	}
	
	
	@ApiOperation(value = "发送消息, 使用插件实现延迟消息", notes = "发送消息")
	@GetMapping(value = "/send2/{message}")
	public String send2(@PathVariable(value = "message") String message) {
		
		
		JSONObject json = new JSONObject();
		json.put("messageData", message);
		json.put("messageId", UUID.randomUUID().toString());
		json.put("createTime", formatter.format(LocalDateTime.now()));
		
		
		Message msg = MessageBuilder.withBody(json.toJSONString().getBytes(StandardCharsets.UTF_8))
				// 持久化消息
				.setDeliveryMode(MessageDeliveryMode.PERSISTENT)
				// 设置延迟时间 设置 5 秒
				.setHeader("x-delay", String.valueOf(5 * 1000)).build();
		
		template.convertAndSend(DelayRabbitConfig.DELAY_TEST_EXCHANGE, DelayRabbitConfig.DELAY_TEST_QUEUE, msg);
		
		log.info("方法二：延迟消息发送成功,发送时间：{}", formatter.format(LocalDateTime.now()));
		return message;
	}
	
	
}
