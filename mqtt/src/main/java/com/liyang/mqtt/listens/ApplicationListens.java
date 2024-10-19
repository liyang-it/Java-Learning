package com.liyang.mqtt.listens;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * <h2>监听程序启动/销毁事件</h2>
 * <p>
 *
 * </p>
 *
 * @author LiYang
 * @createTime 2024年10月19日 3:22 下午
 */
@Component
public class ApplicationListens implements CommandLineRunner, DisposableBean {
    private final static Logger LOGGER = LoggerFactory.getLogger(ApplicationListens.class);

    private final MqttClient mqttClient;

    public ApplicationListens(MqttClient mqttClient) {
        this.mqttClient = mqttClient;
    }

    //应用启动成功后的回调
    @Override
    public void run(String... args) throws Exception {
        LOGGER.info("应用启动成功");
    }

    //应用启动关闭前的回调
    @Override
    public void destroy() throws Exception {
        LOGGER.info("应用正在关闭");

        // 手动关闭MQTT，也可以不需要写，依赖注入会自动销毁
        mqttClient.close();
    }
}

