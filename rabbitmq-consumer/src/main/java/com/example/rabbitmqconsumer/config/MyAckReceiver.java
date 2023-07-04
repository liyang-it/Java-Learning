package com.example.rabbitmqconsumer.config;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

import java.io.*;

/**
 * <h2>消息接收处理类</h2>
 * <p>
 *
 * </p>
 *
 * @author Evan <1922802352@qq.com>
 * @since 2023年07月04日 14:16
 */
@Component
@Slf4j
public class MyAckReceiver implements ChannelAwareMessageListener {
	@Override
	public void onMessage(Message message, Channel channel) throws Exception {
		// Channel类 文档 https://www.rabbitmq.com/api-guide.html, https://www.rabbitmq.com/getstarted.html
		long deliveryTag = message.getMessageProperties().getDeliveryTag();
		try {
			byte[] body = message.getBody();
			 ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(body));
			String json = ois.readObject().toString();
			ois.close();
			
			String queueName = message.getMessageProperties().getConsumerQueue();
			
			log.info("消息手动确认，消息队列名: {} MyAckReceiver 接收处理消息: {}", queueName, json);
			
			
			if("order.test".equals(queueName)){
				log.info("消息手动确认，执行 TestDirectQueue 队列中的消息业务流程");
			}
			
			// 调用  channel.basicAck 设置此消息为消费成功
			// 第二个参数，手动确认可以被批处理，当该参数为 true 时，则可以一次性确认 delivery_tag 小于等于传入值的所有消息
			channel.basicAck(deliveryTag, true);
			
			// 第二个参数，true会重新放回队列，所以需要自己根据业务逻辑判断什么时候使用拒绝
			// channel.basicReject(deliveryTag, true);
			
		} catch (Exception e) {
			channel.basicReject(deliveryTag, false);
			e.printStackTrace();
		}
	}
}
