package com.example.rabbitmqconsumer.receiver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * <h2>延迟消息监听器 - 使用 延迟插件实现</h2>
 * <p>
 *
 * </p>
 *
 * @author Evan <1922802352@qq.com>
 * @since 2023年07月05日 16:40
 */
@Component
@Slf4j
public class DelayReceiver {
	public final static String DELAY_TEST_QUEUE = "delay.test.queue";
	public final static String DELAY_TEST_EXCHANGE = "delay.test.exchange";
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * 队列监听延迟消息
	 */
	@RabbitListener(bindings = @QueueBinding(value = @Queue(name = DELAY_TEST_QUEUE), exchange = @Exchange(name = DELAY_TEST_EXCHANGE, delayed = "true"), key = DELAY_TEST_QUEUE))
	@RabbitHandler
	public void listenerDie(Message message) {
		log.info("方法二：使用插件实现延迟消息,队列 [{}]  消费时间：[{}] 监听到消息：[{}]", DELAY_TEST_QUEUE, formatter.format(LocalDateTime.now()), message);
	}
	
	
}
