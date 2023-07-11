package com.example.rabbitmqconsumer.receiver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

/**
 * <h2>直联型 死信交换机消息监听器</h2>
 * <p>
 *  用于监听处理 队列名为：DirectErrorMsgQueue， 交换机为：，路由键为： 的死信消息
 * </p>
 *
 * @author Evan <1922802352@qq.com>
 * @since 2023年07月04日 10:41
 */
@Component
@Slf4j
public class DieMessageDirectReceiver {
	
	public static final String DIRECT_ORDER_PAY_QUEUE = "direct.order.pay.queue";
	public static final String DIRECT_ORDER_PAY_TTL_DIE_QUEUE = "direct.order.pay.ttl.die.queue";
	public static final String DIRECT_ORDER_PAY_EXCHANGE = "direct.order.pay.exchange";
	public static final String DIRECT_ORDER_PAY_TTL_DIE_EXCHANGE = "direct.order.pay.ttl.die.exchange";
	
	public static final String DIRECT_CHANGE_ORDER_STATUS_QUEUE = "direct.change.order.status.queue";
	public static final String DIRECT_CHANGE_ORDER_STATUS_FAIL_QUEUE = "direct.change.order.status.fail.queue";
	public static final String DIRECT_CHANGE_ORDER_STATUS_EXCHANGE = "direct.change.order.status.exchange";
	public static final String DIRECT_CHANGE_ORDER_STATUS_FAIL_EXCHANGE = "direct.change.order.status.fail.exchange";
	
	
	/**
	 * 订单支付消息正常消费 队列监听
	 * @param json 消息
	 */
	@RabbitListener(bindings = @QueueBinding(
			value = @Queue(name = DIRECT_ORDER_PAY_QUEUE, durable = "true"),
			exchange = @Exchange(value = DIRECT_ORDER_PAY_EXCHANGE),
			key = DIRECT_ORDER_PAY_QUEUE
	))
	@RabbitHandler
	public void listener(Message json){
		log.info("订单支付消息正常消费 - 队列：[{}] 接收到消息：{}", DIRECT_ORDER_PAY_QUEUE, json);
	}
	
	
	/**
	 * 订单支付消息超时未消费 死信队列监听,要测试这个 监听，先启动发布者客户端发送支付消息，等待消息过期，在启动这个消费者客户端就可以监听到已过期的死信队列消息
	 * @param json 消息
	 */
	@RabbitListener(bindings = @QueueBinding(
			value = @Queue(name = DIRECT_ORDER_PAY_TTL_DIE_QUEUE, durable = "true"),
			exchange = @Exchange(value = DIRECT_ORDER_PAY_TTL_DIE_EXCHANGE),
			key = DIRECT_ORDER_PAY_TTL_DIE_QUEUE
	))
	@RabbitHandler
	public void listenerOrderPayTtlDie(Message json){
		log.info("订单支付超时消息未消费 - 死信队列：[{}] 接收到消息：{}", DIRECT_ORDER_PAY_TTL_DIE_QUEUE, json);
	}
	
	
	/**
	 * 更改订单状态消息正常消费 队列监听，如果这个队列，改为了手动确认消费消息模式，需要把这个监听去掉，不然有些消息还是会被分发到这里消费，没有手动确认
	 * @param json 消息
	 
	@RabbitListener(bindings = @QueueBinding(
			value = @Queue(name = DIRECT_CHANGE_ORDER_STATUS_QUEUE, durable = "true"),
			exchange = @Exchange(value = DIRECT_CHANGE_ORDER_STATUS_EXCHANGE),
			key = DIRECT_CHANGE_ORDER_STATUS_QUEUE
	))
	@RabbitHandler
	public void listenerDirectChangeOrderStatus(Message json){
		log.info("更改订单状态消息正常消费 - 队列：[{}] 接收到消息：{}", DIRECT_CHANGE_ORDER_STATUS_QUEUE, json);
	}
	 */
	
	/**
	 * 更改订单状态消息正常消费 失败，死信交换机 队列监听
	 * @param json 消息
	 */
	@RabbitListener(bindings = @QueueBinding(
			value = @Queue(name = DIRECT_CHANGE_ORDER_STATUS_FAIL_QUEUE, durable = "true"),
			exchange = @Exchange(value = DIRECT_CHANGE_ORDER_STATUS_FAIL_EXCHANGE),
			key = DIRECT_CHANGE_ORDER_STATUS_FAIL_QUEUE
	))
	@RabbitHandler
	public void listenerDirectChangeOrderStatusFail(Message json){
		log.info("更改订单状态 死信队列消息 - 队列：[{}] 接收到消息：{}", DIRECT_CHANGE_ORDER_STATUS_FAIL_QUEUE, json);
	}
}
