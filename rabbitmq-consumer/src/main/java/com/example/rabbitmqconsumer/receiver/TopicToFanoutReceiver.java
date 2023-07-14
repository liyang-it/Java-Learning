package com.example.rabbitmqconsumer.receiver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * <h2>使用topic主题交换机实现 fanout发布订阅模式交换机功能</h2>
 * <p>
 *
 * </p>
 *
 * @author Evan <1922802352@qq.com>
 * @since 2023年07月05日 16:40
 */
@Component
@Slf4j
public class TopicToFanoutReceiver {
	
	
	private static final String TOPIC_FANOUT_DOG_QUEUE = "topic.fanout.dog.queue";
	private static final String TOPIC_FANOUT_CAT_QUEUE = "topic.fanout.cat.queue";
	private static final String TOPIC_FANOUT_EXCHANGE = "topic.fanout.exchange";
	
	
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	
	
	@RabbitListener(bindings = @QueueBinding(value = @Queue(name = TOPIC_FANOUT_DOG_QUEUE, durable = "true"), exchange = @Exchange(name = TOPIC_FANOUT_EXCHANGE), key = TOPIC_FANOUT_DOG_QUEUE))
	@RabbitHandler
	public void listenerDog(Message message) {
		String json = new String(message.getBody());
		log.info("topic实现发布订阅模式交换机功能 ---- 队列： {}, 接收时间：{}, 消息：{}", TOPIC_FANOUT_DOG_QUEUE, formatter.format(LocalDateTime.now()), json);
	}
	
	
	@RabbitListener(bindings = @QueueBinding(value = @Queue(name = TOPIC_FANOUT_CAT_QUEUE, durable = "true"), exchange = @Exchange(name = TOPIC_FANOUT_EXCHANGE), key = TOPIC_FANOUT_CAT_QUEUE))
	@RabbitHandler
	public void listenerCat(Message message) {
		String json = new String(message.getBody());
		log.info("topic实现发布订阅模式交换机功能 ---- 队列： {}, 接收时间：{}, 消息：{}", TOPIC_FANOUT_CAT_QUEUE, formatter.format(LocalDateTime.now()), json);
	}
	
	
}
