package com.example.rabbitmqproducer.controller;

import com.alibaba.fastjson2.JSONObject;
import com.example.rabbitmqproducer.exchange.DieMessageDirectRabbitConfig;
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
@RequestMapping(value = "/direct/die")
@Api(tags = "(死信)直连型交换机发送消息控制层")
@Slf4j
public class DieDirectExChangeMessageController {
	
	private final RabbitTemplate template;
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	
	public DieDirectExChangeMessageController(RabbitTemplate template) {
		this.template = template;
	}
	
	
	@ApiOperation(value = "发送消息，有效期15秒未消费，消息进入到对应死信队列(测试方法，只启动发布者客户端发送消息，打开后台管理查看队列消息状态)", notes = "发送消息")
	@GetMapping(value = "/send/{message}")
	public String send(@PathVariable(value = "message") String message) {
		
		
		String msgId = UUID.randomUUID().toString();
		JSONObject json = new JSONObject();
		json.put("messageData", message);
		json.put("messageId", msgId);
		json.put("createTime", formatter.format(LocalDateTime.now()));
		
		
		Message msg = MessageBuilder.withBody(json.toJSONString().getBytes(StandardCharsets.UTF_8))
				// 持久化消息
				.setDeliveryMode(MessageDeliveryMode.PERSISTENT)
				// 有效期
				.setExpiration("15000").build();
		
		template.convertAndSend(DieMessageDirectRabbitConfig.DIRECT_ORDER_PAY_EXCHANGE, DieMessageDirectRabbitConfig.DIRECT_ORDER_PAY_QUEUE, msg);
		
		log.info("消息发送成功");
		return message;
	}
	
	@ApiOperation(value = "发送消息，消费者端手动确认消费该消息，途中将该消息标记为死信状态，流入到对应死信队列处理", notes = "发送消息")
	@GetMapping(value = "/send2/{message}")
	public String send2(@PathVariable(value = "message") String message) {
		
		
		String msgId = UUID.randomUUID().toString();
		JSONObject json = new JSONObject();
		json.put("messageData", message);
		json.put("messageId", msgId);
		json.put("createTime", formatter.format(LocalDateTime.now()));
		
		
		Message msg = MessageBuilder.withBody(json.toJSONString().getBytes(StandardCharsets.UTF_8))
				// 持久化消息
				.setDeliveryMode(MessageDeliveryMode.PERSISTENT)
				// 有效期
				.setExpiration("600000").build();
		
		template.convertAndSend(DieMessageDirectRabbitConfig.DIRECT_CHANGE_ORDER_STATUS_EXCHANGE, DieMessageDirectRabbitConfig.DIRECT_CHANGE_ORDER_STATUS_QUEUE, msg);
		
		log.info("消息发送成功");
		return message;
	}
	
	
}
