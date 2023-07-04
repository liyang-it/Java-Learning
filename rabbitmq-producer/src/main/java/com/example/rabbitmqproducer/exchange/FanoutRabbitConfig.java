package com.example.rabbitmqproducer.exchange;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <h2>扇形交换机</h2>
 * <p>
 *  创建三个队列： fanout.A, fanout.B, fanout.C    <br>
 *  将这三个队列都绑定在 fanoutExchange交换机上   <br>
 *  因为是扇形交换机，路由键无需配置，配置也不起作用, 将消息发送到对应交换机，交换机会把消息分发到该交换机所有队列中去<br>
 * </p>
 *
 * @author Evan <1922802352@qq.com>
 * @since 2023年07月04日 11:50
 */
@Configuration
public class FanoutRabbitConfig {

	@Bean
	public Queue fanoutA(){
		return new Queue("fanout.A", true);
	}
	
	@Bean
	public Queue fanoutB(){
		return new Queue("fanout.B", true);
	}
	
	@Bean
	public Queue fanoutC(){
		return new Queue("fanout.C", true);
	}
	
	@Bean
	public FanoutExchange fanoutExchange(){
		return new FanoutExchange("fanoutExchange");
	}
	
	@Bean
	public Binding bindingA(){
		return BindingBuilder.bind(fanoutA()).to(fanoutExchange());
	}
	
	@Bean
	public Binding bindingB(){
		return BindingBuilder.bind(fanoutB()).to(fanoutExchange());
	}
	
	@Bean
	public Binding bindingC(){
		return BindingBuilder.bind(fanoutC()).to(fanoutExchange());
	}
}
