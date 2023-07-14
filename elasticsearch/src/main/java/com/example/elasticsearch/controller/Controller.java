package com.example.elasticsearch.controller;

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
		
		writer.println("<a target='_blank' href='https://www.elastic.co/guide/cn/elasticsearch/guide/current/_more_complicated_searches.html'>Elastic Search官方文档</a></br>");
		writer.println("<a target='_blank' href='https://www.easy-es.cn/pages/7ead0d/#%E7%AE%80%E4%BB%8B'>Easy-es文档</a></br>");
		writer.println("<a target='_blank' href='https://docs.spring.io/spring-data/elasticsearch/docs/current/reference/html/#preface.metadata'>SpringBoot Data ElasticSearch文档</a></br>");
		writer.println("<a target='_blank' href='http://127.0.0.1:8080/es/swagger-ui.html'>swagger文档</a></br>");
		writer.flush();
	}
}
