package com.example.rabbitmqconsumer.receiver;

import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * <h2>扇型交换机监听器(订阅模式)</h2>
 * <p>
 *
 * </p>
 *
 * @author Evan <1922802352@qq.com>
 * @since 2023年07月04日 11:30
 */
@Component
@Slf4j
public class FanoutReceiver {
	
	@RabbitListener(queues = "fanout.A")
	@RabbitHandler
	public void topicManMessageA(JSONObject json) {
		log.info("扇型交换机 队列：[{}] 接收到消息：{}", "fanout.A", json);
	}
	
	@RabbitListener(queues = "fanout.B")
	@RabbitHandler
	public void topicManMessageB(JSONObject json) {
		log.info("扇型交换机 队列：[{}] 接收到消息：{}", "fanout.B", json);
	}
	
	@RabbitListener(queues = "fanout.C")
	@RabbitHandler
	public void topicManMessageC(JSONObject json) {
		log.info("扇型交换机 队列：[{}] 接收到消息：{}", "fanout.C", json);
	}
	
	
}
