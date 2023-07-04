package com.example.rabbitmqproducer.exchange;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <h2>直连型交换机</h2>
 * <p>
 *  直连型交换机，顾名思义，消息发送之后直接到达对应 交换机绑定的队列中
 * </p>
 *
 * @author Evan <1922802352@qq.com>
 * @since 2023年07月04日 9:29
 */
@Configuration
public class DirectRabbitConfig {
	
	
	/**
	 * 创建一个队列，起名为: TestDirectQueue
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
	
	@Bean
	public DirectExchange lonelyDirectExchange(){
		return new DirectExchange("lonelyDirectExchange");
	}
	
}
