package com.example.rabbitmqproducer.exchange;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * <h2>延迟消息队列  直接使用 延迟插件</h2>
 * <p>
 * RabbitMQ 延迟消息需要安装delay插件: <a href=https://github.com/rabbitmq/rabbitmq-delayed-message-exchange>官方插件安装</a><br>
 * 方法二：使用延迟插件，发送消息的时候 设置 x-delay 参数，就可以实现了,使用 直连交换机演示,其他交换机都可以
 * </p>
 *
 * @author Evan <1922802352@qq.com>
 * @since 2023年07月05日 16:12
 */
@Configuration
public class DelayRabbitConfig {
	public final static String DELAY_TEST_QUEUE = "delay.test.queue";
	public final static String DELAY_TEST_EXCHANGE = "delay.test.exchange";
	
	@Bean("delayTestQueue")
	public Queue delayTestQueue() {
		return QueueBuilder.durable(DELAY_TEST_QUEUE).build();
	}
	
	@Bean("delayTestExchange")
	public CustomExchange delayTestExchange() {
		Map<String, Object> args = new HashMap<>(1);
		// 自定义交换机的类型，指定分发方式
		args.put("x-delayed-type", "direct");
		// 创建自定义交换机，类型为 x-delayed-message
		return new CustomExchange(DELAY_TEST_EXCHANGE, "x-delayed-message", true, false, args);
	}
	
	@Bean
	public Binding bindingDelayTestExchange() {
		return BindingBuilder.bind(delayTestQueue()).to(delayTestExchange()).with(DELAY_TEST_QUEUE).noargs();
	}
	
	
}
