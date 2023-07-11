package com.example.rabbitmqproducer.exchange;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <h2>直连型交换机</h2>
 * <p>
 *  直连型交换机，顾名思义，消息发送之后直接到达对应 交换机绑定的队列中<br>
 *  在RabbitMQ中，队列和路由是两个关键概念，它们之间存在一定的关系。
 *  队列（Queue）是RabbitMQ中用于存储消息的地方。当生产者发送消息时，消息会被发送到一个或多个交换机（Exchange），然后交换机根据特定的规则将消息路由到一个或多个队列。队列是消息的终点，消费者从队列中获取消息并进行处理。
 *  路由（Routing）是决定消息从交换机到队列的过程。在RabbitMQ中，路由是通过绑定（Binding）来实现的。绑定是交换机和队列之间的关联关系，它定义了消息从交换机到队列的路由规则。绑定通常使用绑定键（Binding Key）来指定路由规则。
 *  当消息被发送到交换机时，交换机会根据绑定键将消息路由到一个或多个队列。这个过程称为路由。路由的方式取决于交换机的类型和绑定的配置。不同类型的交换机有不同的路由规则，例如直连交换机（Direct Exchange）根据绑定键的完全匹配来路由消息，而主题交换机（Topic Exchange）根据绑定键的模式匹配来路由消息。
 *  因此，队列和路由之间的关系是：队列通过绑定与交换机关联，绑定定义了消息从交换机到队列的路由规则。
 *  一个交换机可以有很多个队列，但是我怎么把消息发送到对应队列呢?这时候就需要路由，交换机绑定队列，并且指定一个路由键，发送消息的时候 我就可以根据路由键把我的消息发到对应队列
 * </p>
 *
 * @author Evan <1922802352@qq.com>
 * @since 2023年07月04日 9:29
 */
@Configuration
public class DirectRabbitConfig {
	
	
	/**
	 * 创建一个队列，起名为: TestDirectQueue, 用于测试 直连交换机
	 * @return 返回队列对象{@link Queue}
	 */
	@Bean
	public Queue testDirectQueue(){
		// durable 是否持久化，默认是false；持久化会存储在磁盘上，当消息代理重启时仍然存在
		// exclusive 默认false，只能被当前创建的连接使用，而且当连接关闭后队列立即被删除
		// autoDelete 是否自动删除，当没有生产者或者消费者使用此队列，该队列会自动删除
		// return new Queue("TestDirectQueue", true, true, false);
		
		// 一般设置一下队列的持久化，其余两个就是默认false
		return new Queue("TestDirectQueue", true);
	}
	
	/**
	 * 创建一个队列，起名为: DirectMsgQueue
	 * @return 返回队列对象{@link Queue}
	 */
	@Bean
	public Queue directMsgQueue(){
		return QueueBuilder.durable("DirectMsgQueue")
				.build();
	}
	
	
	/**
	 * 创建一个直连交换机，起名为: TestDirectExchange
	 * @return 交换机{@link DirectExchange}
	 */
	@Bean
	public DirectExchange testDirectExchange(){
		// 开启持久化，开启自动删除
		return new DirectExchange("TestDirectExchange", true, true);
	}
	
	
	
	/**
	 * 将 队列与交换机 绑定，并设置用于匹配路由键: TestDirectRouting
	 * @return {@link Binding}
	 */
	@Bean
	public Binding bindingDirect(){
		return BindingBuilder.bind(testDirectQueue()).to(testDirectExchange()).with("TestDirectRouting");
	}
	
	/**
	 * 将 队列与交换机 绑定，并设置用于匹配路由键: DirectMsgQueue
	 * @return {@link Binding}
	 */
	@Bean
	public Binding bindingDirect2(){
		return BindingBuilder.bind(directMsgQueue()).to(testDirectExchange()).with("DirectMsgQueue");
	}
	
}
