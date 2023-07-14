package com.example.elasticsearch.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <h2></h2>
 * <p>
 * es连接配置信息，这里配置获取的是 Easy-es的连接信息，也可以获取其他的,主要用于获取连接地址，没有业务操作
 * </p>
 *
 * @author Evan <1922802352@qq.com>
 * @since 2023年07月12日 11:39
 */
@ConfigurationProperties(prefix = "easy-es")
public class EsConnection {
	private String address;
	
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
}
