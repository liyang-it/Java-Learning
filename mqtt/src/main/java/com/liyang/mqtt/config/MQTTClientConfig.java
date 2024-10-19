package com.liyang.mqtt.config;

import com.liyang.mqtt.util.SSLUtils;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * <h2>MQTT客户端</h2>
 * <p>
 *
 * </p>
 *
 * @author LiYang
 * @createTime 2024年10月19日 2:02 下午
 */
@Component
public class MQTTClientConfig {
    private final MQTTConfig config;
    private final static Logger LOGGER = LoggerFactory.getLogger(MQTTClientConfig.class);

    public MQTTClientConfig(MQTTConfig config) {
        this.config = config;
    }

    @Bean
    public MqttClient mqttClient() throws MqttException {

        String clientId = MqttClient.generateClientId();

        // 持久化
        MemoryPersistence persistence = new MemoryPersistence();

        // MQTT 连接选项
        MqttConnectOptions connOpts = new MqttConnectOptions();

        // 设置认证信息
        connOpts.setUserName(config.getUsername());
        connOpts.setPassword(config.getPassword().toCharArray());

        // 设置CA证书
        try {
            // 证书通过MQTT服务中下载
            String caCrtFile = MQTTClientConfig.class.getClassLoader().getResource("").getPath().concat("emqxsl-ca.crt");
            connOpts.setSocketFactory(SSLUtils.getSingleSocketFactory(caCrtFile));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // 建立连接
        MqttClient client = new MqttClient(config.getUrl(), clientId, persistence);

        // 消息回调，处理接收订阅的主题消息
        client.setCallback(new MessageCallback());

        // 确认连接
        client.connect(connOpts);

        LOGGER.info("MQTT连接状态：[{}],  地址：[{}], 客户端ID：[{}] ", client.isConnected(), config.getUrl(), clientId);

        // 订阅主题
        client.subscribe(config.getTopic1(), config.getQos());
        client.subscribe(config.getTopic2(), config.getQos());

        return client;
    }
}
