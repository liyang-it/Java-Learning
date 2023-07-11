package com.example.rabbitmqproducer.exchange;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <h2>延迟消息队列  使用 topic主题型 死信交换机</h2>
 * <p>
 *  RabbitMQ 延迟消息需要安装delay插件: <a href=https://github.com/rabbitmq/rabbitmq-delayed-message-exchange>官方插件安装</a><br>
 *  延迟消息只能用 DelayExchange交换机和 死信交换机实现，这个demo使用 topic 死信交换机来实现 延迟消息,比较麻烦
 *  方法一：创建正常 topic队列，并设置 死信交换机和死信路由键，将正常的topic队列改为手动确认消费消息模式发送消息设置消息的过期时间，期间收到消息后不消费，将消息重新放入到队列中排队去,直到消息到指定时间过期进入死信队列才消费,这样就是实现了 延迟消息，这个方式不需要 安装延迟插件，但是缺点也明显，因为将消息重新放入到队列进行排队，可能会延迟会不准，队列任务不要太多
 *  方法二：直接使用 延迟插件
 * </p>
 *
 * @author Evan <1922802352@qq.com>
 * @since 2023年07月05日 16:12
 */
@Configuration
public class DelayTopicRabbitConfig {
	
	public final static  String TOPIC_TEST_QUEUE = "topic.test.queue";
	public final static String TOPIC_TEST_EXCHANGE = "topic.test.exchange";
	
	public final static  String DELAY_TEST_DIE_QUEUE = "topic.delay.test.die.queue";
	public final static String DELAY_TEST_DIE_EXCHANGE = "topic.delay.test.die.exchange";
	
	/**
	 * 创建一个 topic 队列，增加死信交换机
	 * 这个实现逻辑是： 新建一个 队列，然后设置 死信机制，将该队列同时设置为手动确认消费消息，
	 * 每次消费这个消息都标记为死信，流入到死信队列实现延迟消息
	 */
	@Bean
	public Queue topicTestQueue(){
		return QueueBuilder.durable(TOPIC_TEST_QUEUE)
				// 死信交换机
				.deadLetterExchange(DELAY_TEST_DIE_EXCHANGE)
				// 死信路由
				.deadLetterRoutingKey(DELAY_TEST_DIE_QUEUE)
				.build();
	}
	
	@Bean
	public Queue topicDelayTestDieQueue(){
		return QueueBuilder.durable(DELAY_TEST_DIE_QUEUE).build();
	}
	
	@Bean
	public TopicExchange topicTestExchange(){
		return new TopicExchange(TOPIC_TEST_EXCHANGE);
	}
	
	@Bean
	public TopicExchange topicDelayTestDieExchange(){
		return new TopicExchange(DELAY_TEST_DIE_EXCHANGE);
	}
	

	
	
	/**
	 * 将 队列 绑定交换机，路由键为 topic.test.queue，只要发送消息 指定的路由键 是 topic.test.queue 就会分发到这个队列，这样就是实现了 直连型交换机
	 */
	@Bean
	public Binding bindingTopicTestExchange(){
		return BindingBuilder.bind(topicTestQueue()).to(topicTestExchange()).with(TOPIC_TEST_QUEUE);
	}
	
	/**
	 *
	 * @return 死信路由绑定死信交换机
	 */
	@Bean
	public Binding bindingTopicDelayTestDIeExchange(){
		return BindingBuilder.bind(topicDelayTestDieQueue()).to(topicDelayTestDieExchange()).with(DELAY_TEST_DIE_QUEUE);
	}
	
	
}
