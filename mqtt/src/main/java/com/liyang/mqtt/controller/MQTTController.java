package com.liyang.mqtt.controller;

import com.liyang.mqtt.config.MQTTClientConfig;
import com.liyang.mqtt.config.MQTTConfig;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <h2>MQTT测试</h2>
 * <p>
 *
 * </p>
 *
 * @author LiYang
 * @createTime 2024年10月19日 3:15 下午
 */
@RequestMapping(value = "/mqtt")
@RestController
public class MQTTController {
    private final MQTTConfig mqttConfig;
    private final MqttClient mqttClient;

    public MQTTController(MQTTConfig mqttConfig, MqttClient mqttClient) {
        this.mqttConfig = mqttConfig;
        this.mqttClient = mqttClient;
    }

    @GetMapping(value = "/send")
    public String send(String content) throws MqttException {
        // 发送消息
        MqttMessage message = new MqttMessage(content.getBytes());
        message.setQos(mqttConfig.getQos());
        mqttClient.publish(mqttConfig.getTopic1(), message);
        mqttClient.publish(mqttConfig.getTopic2(), message);
        return "success";
    }


}
