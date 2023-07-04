package com.example.rabbitmqconsumer.receiver;

import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * <h2>直联型交换机消息监听器 - 复制监听器，但是还是监听同一个队列，多个监听器监听同一个队列，采用轮询的方式对消息进行消费，不存在重复消费</h2>
 * <p>
 *
 * </p>
 *
 * @author Evan <1922802352@qq.com>
 * @since 2023年07月04日 10:41
 */
@Component
@RabbitListener(queues = "TestDirectQueue")
@Slf4j
public class DirectReceiverCopy2Only {
	
	@RabbitHandler
	public void listener(JSONObject json){
		log.info("直连型交换机 copy-only2 队列：[{}] 接收到消息：{}", "TestDirectQueue", json);
	}
}
