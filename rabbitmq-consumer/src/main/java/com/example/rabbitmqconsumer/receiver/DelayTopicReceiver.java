package com.example.rabbitmqconsumer.receiver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * <h2>延迟消息监听器 - 使用topic主题交换机 死信实现</h2>
 * <p>
 *
 * </p>
 *
 * @author Evan <1922802352@qq.com>
 * @since 2023年07月05日 16:40
 */
@Component
@Slf4j
public class DelayTopicReceiver {

	public final static  String TOPIC_TEST_QUEUE = "topic.test.queue";
	public final static String TOPIC_TEST_EXCHANGE = "topic.test.exchange";
	
	public final static  String DELAY_TEST_DIE_QUEUE = "topic.delay.test.die.queue";
	public final static String DELAY_TEST_DIE_EXCHANGE = "topic.delay.test.die.exchange";
	
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	
	/*
	// 需要将这个队列消息确认改为手动模式，无论什么情况都将消息重新放入到队列中心排队，直到消息到指定时间过期进入死信队列才消费
	@RabbitListener(bindings = @QueueBinding(
			value = @Queue(name = TOPIC_TEST_QUEUE),
			exchange = @Exchange(name = TOPIC_TEST_EXCHANGE),
			key = TOPIC_TEST_QUEUE
	))
	@RabbitHandler
	public void listener(Message message){
		log.info("topic正常队列 ：[{}] 监听到消息：[{}]", TOPIC_TEST_QUEUE, message);
	}
	*/
	
	
	/**
	 * 死信延迟消息监听
	 */
	@RabbitListener(bindings = @QueueBinding(
			value = @Queue(name = DELAY_TEST_DIE_QUEUE),
			exchange = @Exchange(name = DELAY_TEST_DIE_EXCHANGE),
			key = DELAY_TEST_DIE_QUEUE
	))
	@RabbitHandler
	public void listenerDie(Message message){
		log.info("方法一：topic 死信延迟队列 [{}]  消费时间：[{}] 监听到消息：[{}]", DELAY_TEST_DIE_QUEUE, formatter.format(LocalDateTime.now()), message);
	}
	
	
}
