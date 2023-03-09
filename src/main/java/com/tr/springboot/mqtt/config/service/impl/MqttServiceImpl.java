package com.tr.springboot.mqtt.config.service.impl;

import com.tr.springboot.mqtt.config.entity.MqttMsg;
import com.tr.springboot.mqtt.config.service.MqttService;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author taorun
 * @date 2023/1/29 8:41
 */
@Service
public class MqttServiceImpl implements MqttService {

    @Resource
    private MqttClient mqttClient;

    @Override
    public void publish(MqttMsg mqttMsg) {
        try {
            mqttClient.publish(mqttMsg.getTopic(), mqttMsg.getMessage().getBytes(), mqttMsg.getQos(), mqttMsg.getRetained());
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void publishForCompletion(MqttMsg mqttMsg) {
        MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setQos(mqttMsg.getQos());
        mqttMessage.setRetained(mqttMsg.getRetained());
        mqttMessage.setPayload(mqttMsg.getMessage().getBytes());
        // 主题的目的地，用于发布/订阅信息
        MqttTopic mqttTopic = mqttClient.getTopic(mqttMsg.getTopic());
        // 提供一种机制来跟踪消息的传递进度
        // 用于在以非阻塞方式（在后台运行）执行发布是跟踪消息的传递进度
        MqttDeliveryToken token;
        try {
            // 将指定消息发布到主题，但不等待消息传递完成，返回的 token 可用于跟踪消息的传递状态
            // 一旦此方法干净地返回，消息就已被客户端接受发布，当连接可用，将在后台完成消息传递
            token = mqttTopic.publish(mqttMessage);
            token.waitForCompletion();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void subscribe(String... topics) {
        try {
            mqttClient.subscribe(topics);
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void unsubscribe(String... topics) {
        try {
            mqttClient.unsubscribe(topics);
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void connect() {
        try {
            mqttClient.connect();
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void disconnect() {
        try {
            mqttClient.disconnect();
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }

}
