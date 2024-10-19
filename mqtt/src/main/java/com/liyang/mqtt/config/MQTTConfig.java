package com.liyang.mqtt.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * <h2>MQTT配置信息</h2>
 * <p>
 *
 * </p>
 *
 * @author LiYang
 * @createTime 2024年10月19日 1:53 下午
 */
@Configuration
@ConfigurationProperties(prefix = "mqtt")
public class MQTTConfig {
    private String url;
    private String username;
    private String password;
    private Integer qos;
    private String topic1;
    private String topic2;


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getQos() {
        return qos;
    }

    public void setQos(Integer qos) {
        this.qos = qos;
    }

    public String getTopic1() {
        return topic1;
    }

    public void setTopic1(String topic1) {
        this.topic1 = topic1;
    }

    public String getTopic2() {
        return topic2;
    }

    public void setTopic2(String topic2) {
        this.topic2 = topic2;
    }
}
