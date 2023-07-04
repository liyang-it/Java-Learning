package com.example.rabbitmqconsumer.receiver;

import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * <h2>主题型交换机监听器</h2>
 * <p>
 *
 * </p>
 *
 * @author Evan <1922802352@qq.com>
 * @since 2023年07月04日 11:30
 */
@Component
@Slf4j
public class TopicReceiver {
	
	@RabbitListener(queues = "topic.man")
	@RabbitHandler
	public void topicManMessage(JSONObject json){
		log.info("主题型交换机 队列：[{}] 接收到消息：{}", "topic.man", json);
	}
	
	@RabbitListener(queues = "topic.woman")
	@RabbitHandler
	public void topicWoManMessage(JSONObject json){
		log.info("主题型交换机 队列：[{}] 接收到消息：{}", "topic.woman", json);
	}
	
	@RabbitListener(queues = "order.test")
	@RabbitHandler
	public void topicTestMessage(JSONObject json){
		log.info("主题型交换机 队列：[{}] 接收到消息：{}", "order.test", json);
	}
}
