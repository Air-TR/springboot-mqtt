package com.tr.springboot.mqtt.config.callback;

import com.tr.springboot.mqtt.config.properties.MqttProperties;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
public class MqttCallBack implements MqttCallbackExtended {

    @Resource
    private ApplicationContext applicationContext;

    @Resource
    private MqttProperties mqttProperties;

    /**
     * 客户端断开连接的回调
     */
    @Override
    public void connectionLost(Throwable throwable) {
        System.out.println(mqttProperties.getClientId() + " 与服务器断开连接，可重连");
    }

    /**
     * 消息到达的回调
     */
    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        System.out.println(String.format("接收消息主题: %s", topic));
        System.out.println(String.format("接收消息Qos: %d", message.getQos()));
        System.out.println(String.format("接收消息内容: %s", new String(message.getPayload()))); // message.toString() 也可以拿到消息内容
        System.out.println(String.format("接收消息retained: %b", message.isRetained()));
    }

    /**
     * 消息发布成功的回调
     */
    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        System.out.println(mqttProperties.getClientId() + " 发布消息成功");
    }

    /**
     * MqttClient 连接完成后操作
     */
    @Override
    public void connectComplete(boolean reconnect, String mqttUrl) {
        try {
            MqttClient mqttClient = applicationContext.getBean(MqttClient.class);
            mqttClient.subscribe(mqttProperties.getTopic(), mqttProperties.getQos());
            System.out.println(mqttProperties.getClientId() + " 已连接");
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }

}
