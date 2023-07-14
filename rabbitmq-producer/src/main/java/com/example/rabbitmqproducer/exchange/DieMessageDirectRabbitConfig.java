package com.example.rabbitmqproducer.exchange;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <h2>直连型交换机 - 模拟死信交换机测试</h2>
 * <p>
 *
 * </p>
 *
 * @author Evan <1922802352@qq.com>
 * @since 2023年07月04日 9:29
 */
@Configuration
public class DieMessageDirectRabbitConfig {
	
	public static final String DIRECT_ORDER_PAY_QUEUE = "direct.order.pay.queue";
	public static final String DIRECT_ORDER_PAY_TTL_DIE_QUEUE = "direct.order.pay.ttl.die.queue";
	public static final String DIRECT_ORDER_PAY_EXCHANGE = "direct.order.pay.exchange";
	public static final String DIRECT_ORDER_PAY_TTL_DIE_EXCHANGE = "direct.order.pay.ttl.die.exchange";
	
	
	public static final String DIRECT_CHANGE_ORDER_STATUS_QUEUE = "direct.change.order.status.queue";
	public static final String DIRECT_CHANGE_ORDER_STATUS_FAIL_QUEUE = "direct.change.order.status.fail.queue";
	public static final String DIRECT_CHANGE_ORDER_STATUS_EXCHANGE = "direct.change.order.status.exchange";
	public static final String DIRECT_CHANGE_ORDER_STATUS_FAIL_EXCHANGE = "direct.change.order.status.fail.exchange";
	
	/**
	 * 创建一个正常队列：模拟 订单支付消息超时未消费，将消息标记为死信
	 *
	 * @return direct.order.pay.queue
	 */
	@Bean
	public Queue directOrderPayQueue() {
		return QueueBuilder.durable(DIRECT_ORDER_PAY_QUEUE)
				// 该队列消息超时未消费，流入到指定死信交换机
				.deadLetterExchange(DIRECT_ORDER_PAY_TTL_DIE_EXCHANGE)
				// 指定死信路由
				.deadLetterRoutingKey(DIRECT_ORDER_PAY_TTL_DIE_QUEUE).build();
	}
	
	
	/**
	 * 死信队列， 模拟订单支付消息超时未支付(未消费)，进入这个队列处理
	 *
	 * @return direct.order.pay.die.queue
	 */
	@Bean
	public Queue directOrderPayTtlDieQueue() {
		return QueueBuilder.durable(DIRECT_ORDER_PAY_TTL_DIE_QUEUE).build();
	}
	
	
	/**
	 * @return 正常 订单支付交换机
	 */
	@Bean
	public DirectExchange directOrderPayExchange() {
		return new DirectExchange(DIRECT_ORDER_PAY_EXCHANGE);
	}
	
	/**
	 * @return 死信 订单支付超时未支付交换机
	 */
	@Bean
	public DirectExchange directOrderPayTtlDieExchange() {
		return new DirectExchange(DIRECT_ORDER_PAY_TTL_DIE_EXCHANGE);
	}
	
	/**
	 * 正常订单支付交换机绑定队列
	 */
	@Bean
	public Binding bindingDirectOrderPayExchange() {
		return BindingBuilder.bind(directOrderPayQueue()).to(directOrderPayExchange()).with(DIRECT_ORDER_PAY_QUEUE);
	}
	
	/**
	 * 支付超时死信交换机绑定队列
	 */
	@Bean
	public Binding bindingDirectOrderPayTtlDieExchange() {
		return BindingBuilder.bind(directOrderPayTtlDieQueue()).to(directOrderPayTtlDieExchange()).with(DIRECT_ORDER_PAY_TTL_DIE_QUEUE);
	}
	
	
	/**
	 * 创建一个正常队列：模拟 订单支付成功，发送更改订单状态消息，途中逻辑发生异常，手动将该队列消息标记为死信
	 *
	 * @return direct.order.pay.queue
	 */
	@Bean
	public Queue directChangeOrderStatusQueue() {
		return QueueBuilder.durable(DIRECT_CHANGE_ORDER_STATUS_QUEUE)
				// 该队列消息 发生异常 标记为死信、或者超时未消息，流入到指定死信交换机
				.deadLetterExchange(DIRECT_CHANGE_ORDER_STATUS_FAIL_EXCHANGE)
				// 指定死信路由
				.deadLetterRoutingKey(DIRECT_CHANGE_ORDER_STATUS_FAIL_QUEUE).build();
	}
	
	
	/**
	 * 创建一个死信队列：模拟 更改订单状态发生逻辑异常，标记为死信，会进入到这个死信队列处理
	 *
	 * @return direct.order.pay.queue
	 */
	@Bean
	public Queue directChangeOrderStatusFailQueue() {
		return QueueBuilder.durable(DIRECT_CHANGE_ORDER_STATUS_FAIL_QUEUE).build();
	}
	
	/**
	 * @return 正常修改 订单状态的交换机，其实这个交换机没必要创建，因为一个 交换机可以绑定多个队列，我这样只是为了模拟功能好区分
	 */
	@Bean
	public DirectExchange directChangeOrderStatusExchange() {
		return new DirectExchange(DIRECT_CHANGE_ORDER_STATUS_EXCHANGE);
	}
	
	
	/**
	 * @return 发生异常修改 订单状态的死信交换机
	 */
	@Bean
	public DirectExchange directChangeOrderStatusFailExchange() {
		return new DirectExchange(DIRECT_CHANGE_ORDER_STATUS_FAIL_EXCHANGE);
	}
	
	
	@Bean
	public Binding bindingDirectChangeOrderStatusExchange() {
		return BindingBuilder.bind(directChangeOrderStatusQueue()).to(directChangeOrderStatusExchange()).with(DIRECT_CHANGE_ORDER_STATUS_QUEUE);
	}
	
	@Bean
	public Binding bindingDirectChangeOrderStatusFailExchange() {
		return BindingBuilder.bind(directChangeOrderStatusFailQueue()).to(directChangeOrderStatusFailExchange()).with(DIRECT_CHANGE_ORDER_STATUS_FAIL_QUEUE);
	}
	
	
}
