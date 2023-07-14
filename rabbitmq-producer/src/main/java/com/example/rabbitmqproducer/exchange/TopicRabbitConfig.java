package com.example.rabbitmqproducer.exchange;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <h2>主题型交换机</h2>
 * <p>
 * 主题交换机可以实现其他交换机的功能，可以实现 直连型交换机功能、等，使用 路由键匹配，消息分发到不同队列中<br>
 * 直连交换机： 生产者发送消息  -> [交换机 对应 队列 ]  -> 消费者监听到消息之后，消费消息<br>
 * 主题交换机： 生产者发送消息  -> [交换机 对应 [路由键1 -> 队列1],[路由键2 -> 队列2],[路由键3 -> 队列3] ]  -> 消费者监听到消息之后，消费消息<br>
 * </p>
 *
 * @author Evan <1922802352@qq.com>
 * @since 2023年07月04日 11:06
 */
@Configuration
public class TopicRabbitConfig {
	
	/**
	 * 绑定键
	 */
	public final static String MAN = "topic.man";
	public final static String WO_MAN = "topic.woman";
	public final static String ORDER_TEST = "order.test";
	
	/**
	 * 创建一个 Topic 主题型队列，键为 topic.man
	 *
	 * @return topic主题队列
	 */
	@Bean
	public Queue topicManQueue() {
		return new Queue(TopicRabbitConfig.MAN, true);
	}
	
	/**
	 * 创建一个 Topic 主题型队列，键为 topic.woman
	 *
	 * @return topic主题队列
	 */
	@Bean
	public Queue topicWoManQueue() {
		return new Queue(TopicRabbitConfig.WO_MAN, true);
	}
	
	@Bean
	public Queue topicOrderTestQueue() {
		return new Queue(TopicRabbitConfig.ORDER_TEST, true);
	}
	
	
	/**
	 * 创建一个 topic主题交换机
	 *
	 * @return {@link TopicExchange}
	 */
	@Bean
	public TopicExchange exchange() {
		return new TopicExchange("topicExchange");
	}
	
	/**
	 * 将 topic.man 的 队列 与交换机绑定，并且绑定的键为 topic.man, 这样只要携带的路由键是 topic.man, 才会分发到该队列
	 *
	 * @return {@link Binding}
	 */
	@Bean
	public Binding bindingExchangeManQueue() {
		return BindingBuilder.bind(topicManQueue()).to(exchange()).with(MAN);
	}
	
	
	/**
	 * 将 topic.woman 的 队列 与交换机绑定，并且绑定的键为通配 topic.#, 这样只要携带的路由键是 topic.开头 都会分发到该队列, 才会分发到该队列
	 *
	 * @return {@link Binding}
	 */
	@Bean
	public Binding bindingExchangeQueue() {
		return BindingBuilder.bind(topicWoManQueue()).to(exchange()).with("topic.#");
	}
	
	@Bean
	public Binding bindingOrderTestExChangeQueue() {
		return BindingBuilder.bind(topicOrderTestQueue()).to(exchange()).with(ORDER_TEST);
	}
	
}
