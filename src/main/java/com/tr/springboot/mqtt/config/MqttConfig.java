package com.tr.springboot.mqtt.config;

import com.tr.springboot.mqtt.config.callback.MqttCallBack;
import com.tr.springboot.mqtt.config.properties.MqttProperties;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
public class MqttConfig {

    @Resource
    private MqttProperties mqttProperties;

    @Resource
    private MqttCallBack mqtCallBack;

    @Bean
    public MqttClient mqttClient() throws MqttException {
        // 创建 MQTT 客户端对象
        MqttClient client = new MqttClient(mqttProperties.getUrl(), mqttProperties.getClientId(), new MemoryPersistence());
        // 连接设置
        MqttConnectOptions options = new MqttConnectOptions();
        // 是否清空 session，设置为 false 表示服务器会保留客户端的连接记录，客户端重连之后能获取到服务器在客户端断开连接期间推送的消息
        // 设置为 true 表示每次连接到服务端都是以新的身份
        options.setCleanSession(true);
        // 设置连接用户名
        options.setUserName(mqttProperties.getUsername());
        // 设置连接密码
        options.setPassword(mqttProperties.getPassword().toCharArray());
        // 设置超时时间，单位为秒
        options.setConnectionTimeout(10);
        // 设置心跳时间 单位为秒，表示服务器每 30 秒向客户端发送心跳判断客户端是否在线
        options.setKeepAliveInterval(30);
        // 设置遗嘱消息的话题，若客户端和服务器之间的连接意外断开，服务器将发布客户端的遗嘱信息
        options.setWill("willTopic", (mqttProperties.getClientId() + "与服务器断开连接").getBytes(), 0, false);
        // 断线自动重连
        options.setAutomaticReconnect(mqttProperties.getAutomaticReconnect());
        // 设置回调
        client.setCallback(mqtCallBack);
        client.connect(options);
        return client;
    }

}
