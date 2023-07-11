package com.example.rabbitmqproducer.exchange;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <h2>使用 topic主题交换机实现 发布订阅模式交换机功能</h2>
 * <p></p>
 *
 * @author Evan <1922802352@qq.com>
 * @since 2023年07月05日 16:12
 */
@Configuration
public class TopicToFanoutRabbitConfig {
	
	private static final String TOPIC_FANOUT_PREFIX = "topic.fanout.#";
	public static final String TOPIC_FANOUT_DOG_QUEUE = "topic.fanout.dog.queue";
	public static final String TOPIC_FANOUT_CAT_QUEUE = "topic.fanout.cat.queue";
	public static final String TOPIC_FANOUT_EXCHANGE = "topic.fanout.exchange";
	
	
	@Bean
	public Queue topicFanoutDogQueue(){
		return QueueBuilder.durable(TOPIC_FANOUT_DOG_QUEUE).build();
	}
	
	@Bean
	public Queue topicFanoutCatQueue(){
		return QueueBuilder.durable(TOPIC_FANOUT_CAT_QUEUE).build();
	}
	
	@Bean
	public TopicExchange topicFanoutExchange(){
		return new TopicExchange(TOPIC_FANOUT_EXCHANGE);
	}
	
	/**
	 * 将 dog 绑定交换机，路由键为 通配符 topic.fanout 开头，只要路由键开头是 topic.fanout ，消息分发到这个队列，这样就是实现了 发布订阅模式交换机
	 */
	@Bean
	public Binding bindingTopicSubDogExchange(){
		return BindingBuilder.bind(topicFanoutDogQueue()).to(topicFanoutExchange()).with(TOPIC_FANOUT_PREFIX);
	}
	
	
	/**
	 * 将 cat 绑定交换机，路由键为 通配符 topic.fanout 开头，只要路由键开头是 topic.fanout ，消息分发到这个队列，这样就是实现了 发布订阅模式交换机
	 */
	@Bean
	public Binding bindingTopicSubCatExchange(){
		return BindingBuilder.bind(topicFanoutCatQueue()).to(topicFanoutExchange()).with(TOPIC_FANOUT_PREFIX);
	}
	
	
}
