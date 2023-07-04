package com.example.rabbitmqproducer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

/**
 * <h2>默认</h2>
 * <p>
 *
 * </p>
 *
 * @author Evan <1922802352@qq.com>
 * @since 2023年07月03日 15:24
 */
@org.springframework.stereotype.Controller
public class Controller {
	@GetMapping(value = "/")
	public void view(HttpServletResponse response) throws IOException {
		// 设置响应内容类型为HTML，并指定字符编码为UTF-8
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter writer = response.getWriter();
		writer.println("<html>");
		// 设置HTML页面的字符编码为UTF-8
		writer.println("<meta charset=\"UTF-8\">");
		writer.println("<title>初始页</title>");
		writer.println("</head>");
		writer.println("<body>");
		writer.println(String.format("当前时间：%s</br>", LocalDateTime.now().toString().replace("T", " ")));
		writer.println("<a target='_blank' href='https://docs.spring.io/spring-amqp/docs/current/reference/html/#amqp-template'>SpringBoot-RabbitMQ官方文档</a></br>");
		writer.println("<a target='_blank' href='https://docs.spring.io/spring-amqp/docs/current/reference/html/#rabbit-template'>SpringBoot-RabbitMQ官方相关资料</a></br>");
		writer.println("<a target='_blank' href='http://127.0.0.1:8888/rabbitmq/prod/swagger-ui.html'>swagger文档</a></br>");
		writer.println("<h3>RabbitMQ 是一个消息中间件，用于在应用程序之间传递消息。在 RabbitMQ 中，有三个重要的概念：交换机（Exchange）、路由键（Routing Key）和队列（Queue)</h3>");
		writer.println("<p>交换机（Exchange）：交换机是 RabbitMQ 接收消息并将其路由到队列的组件。它接收来自生产者的消息，并根据特定的规则将消息路由到一个或多个队列。交换机有不同的类型，如直连交换机、主题交换机、扇形交换机等，每种类型的交换机都有不同的路由规则。</p>");
		writer.println("<p>路由键（Routing Key）：路由键是生产者在发送消息时附加到消息上的一个标识符。交换机根据路由键来决定将消息路由到哪个队列。路由键可以是任意字符串，但在特定的交换机类型中，路由键的格式和含义可能会有所不同。</p>");
		writer.println("<p>队列（Queue）：队列是 RabbitMQ 存储消息的地方。消费者从队列中获取消息并进行处理。队列是消息的终点，当消息到达队列时，它会等待消费者来处理。多个消费者可以订阅同一个队列，但每条消息只能被一个消费者处理。</p>");
		
		writer.flush();
	}
}
