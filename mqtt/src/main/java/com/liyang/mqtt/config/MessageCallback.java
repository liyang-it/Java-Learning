package com.liyang.mqtt.config;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <h2>MQTT订阅主题的消息回调</h2>
 * <p>
 *
 * </p>
 *
 * @author LiYang
 * @createTime 2024年10月19日 2:54 下午
 */
public class MessageCallback implements MqttCallback {

    private final static Logger LOGGER = LoggerFactory.getLogger(MessageCallback.class);

    // 连接丢失
    @Override
    public void connectionLost(Throwable cause) {
        LOGGER.error("MQTT连接丢失 [{}]", cause.getMessage());
    }

    //  收到消息
    @Override
    public void messageArrived(String topic, MqttMessage message) {
        LOGGER.info("MQTT接收到消息, topic: [{}], Qos: [{}], payload: [{}]", topic, message.getQos(), new String(message.getPayload()));
    }

    // 消息传递成功
    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        LOGGER.info("MQTT消息发送成功");
    }
}
