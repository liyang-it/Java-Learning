package com.example.rabbitmqconsumer.config;

import com.example.rabbitmqconsumer.receiver.DelayTopicReceiver;
import com.example.rabbitmqconsumer.receiver.DieMessageDirectReceiver;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <h2>消息监听配置, 不在yml配置中实现</h2>
 * <p>
 *
 * </p>
 *
 * @author Evan <1922802352@qq.com>
 * @since 2023年07月04日 14:15
 */
@Configuration
public class MessageListenerConfig {
	
	private final CachingConnectionFactory connectionFactory;
	private final MyAckReceiver ackReceiver;
	
	public MessageListenerConfig(CachingConnectionFactory connectionFactory, MyAckReceiver ackReceiver) {
		this.connectionFactory = connectionFactory;
		this.ackReceiver = ackReceiver;
	}
	
	@Bean
	public SimpleMessageListenerContainer simpleMessageListenerContainer() {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
		
		// 创建的并发消费者的数量。默认值为 1设置并发消费者的数量。
		// 并发消费者是同时处理消息的消费者的数量。默认情况下，SimpleMessageListenerContainer只创建一个消费者。
		// 通过调用setConcurrentConsumers方法，你可以增加并发消费者的数量，以提高消息处理的并发性。
		// 例如，如果你将setConcurrentConsumers(5)调用应用于SimpleMessageListenerContainer实例，那么它将创建5个并发消费者来处理消息
		// 请注意，增加并发消费者的数量可能会增加系统的负载和资源消耗。你需要根据你的应用程序的需求和系统资源来决定合适的并发消费者数量
		// 如果你同时发送的消息数量少于或等于 concurrentConsumers 条，那么只有 concurrentConsumers 个消费者会处理消息，而其他的消费者将处于空闲状态。可以通过增加并发消费者的数量，以提高消息处理的并发性
		container.setConcurrentConsumers(1);
		
		// 设置消费者数量上限；默认为“并发消费者”。消费者将按需添加。不能小于concurrentConsumers
		container.setMaxConcurrentConsumers(1);
		
		// RabbitMQ 默认是自动确认， 改为手动确认消息
		container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
		
		// SimpleMessageListenerContainer默认使用的是Round-robin（轮询）的消息分发策略。
		// 如果你同时发送的消息数量少于或等于1条，并且只有一个消费者在处理消息，那么其他的消费者将无法接收到消息。
		// 你可以通过调用setPrefetchCount方法来设置每个消费者一次从队列中获取的消息数量，以确保每个消费者都能够接收到消息
		container.setPrefetchCount(1);
		
		// 设置一个队列，将这个队列改为 手动确认模式，可以设置多个，前提是队列都必须已经创建存在的
		container.setQueueNames(DieMessageDirectReceiver.DIRECT_CHANGE_ORDER_STATUS_QUEUE, DelayTopicReceiver.TOPIC_TEST_QUEUE);
		
		// 设置消息确认处理的具体类
		container.setMessageListener(ackReceiver);
		
		
		return container;
	}
}
