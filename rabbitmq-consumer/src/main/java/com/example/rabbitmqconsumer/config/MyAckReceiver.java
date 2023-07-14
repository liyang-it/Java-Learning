package com.example.rabbitmqconsumer.config;

import com.example.rabbitmqconsumer.receiver.DelayTopicReceiver;
import com.example.rabbitmqconsumer.receiver.DieMessageDirectReceiver;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

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
	private final ThreadLocal<Integer> threadLocal = new InheritableThreadLocal<>();
	
	@Override
	public void onMessage(Message message, Channel channel) throws Exception {
		// Channel类 文档 https://www.rabbitmq.com/api-guide.html, https://www.rabbitmq.com/getstarted.html
		long deliveryTag = message.getMessageProperties().getDeliveryTag();
		try {
			// message.getBody() 是具体的消息数据,数据都是 字节格式，赋值的时候需要 将数据转为字节，取值的时候要将字节转为具体数据，一般 json字符串最简单转换
			byte[] body = message.getBody();
			
			// 字节转换为json字符串数据
			String json = new String(body);
			
			// 队列名
			String queueName = message.getMessageProperties().getConsumerQueue();
			
			if (DieMessageDirectReceiver.DIRECT_CHANGE_ORDER_STATUS_QUEUE.equals(queueName)) {
				log.info("消息手动确认，执行 [{}] 队列中的消息业务流程", DieMessageDirectReceiver.DIRECT_CHANGE_ORDER_STATUS_QUEUE);
				threadLocal.set(1);
				throw new RuntimeException("更改订单状态 发生 逻辑错误，标记为死信队列");
				
			} else if (DelayTopicReceiver.TOPIC_TEST_QUEUE.equals(queueName)) {
				threadLocal.set(2);
				throw new RuntimeException("将消息标记为死信状态，流入死信延迟消息队列");
			}
			
			// 调用  channel.basicAck 设置此消息为消费成功
			// 第二个参数，手动确认可以被批处理，当该参数为 true 时，则可以一次性确认 delivery_tag 小于等于传入值的所有消息
			channel.basicAck(deliveryTag, true);
			
			
		} catch (Exception e) {
			
			Integer basicType = threadLocal.get();
			if (basicType == 1) {
				// 消息消费失败,标记消息为死信，并且发送到对应死信队列中去处理
				channel.basicReject(deliveryTag, false);
			} else if (basicType == 2) {
				// 将消息重新排队，等待过期，流入死信消息队列
				channel.basicReject(deliveryTag, true);
			}
			threadLocal.remove();
		}
	}
}
