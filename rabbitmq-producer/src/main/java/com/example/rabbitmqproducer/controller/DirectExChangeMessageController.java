package com.example.rabbitmqproducer.controller;
import com.alibaba.fastjson2.JSONObject;
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
import java.util.HashMap;
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
@RequestMapping(value = "/direct")
@Api(tags = "直连型交换机发送消息控制层")
@Slf4j
public class DirectExChangeMessageController {
	
	private final RabbitTemplate template;
	public DirectExChangeMessageController(RabbitTemplate template){
		this.template = template;
	}
	
	@ApiOperation(value = "将消息携带绑定键值: TestDirectRouting 发送到交换机 TestDirectExchange", notes = "发送消息")
	@GetMapping(value = "/send/{message}")
	public String send(@PathVariable(value = "message") String message){
		
		String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		
		JSONObject json = new JSONObject();
		json.put("messageData", message);
		json.put("messageId", UUID.randomUUID().toString());
		json.put("createTime", createTime);
		// 将消息携带绑定键值: TestDirectRouting 发送到交换机 TestDirectExchange
		template.convertAndSend("TestDirectExchange", "TestDirectRouting", json);
		log.info("消息发送成功");
		return message;
	}
	
	
	
}
