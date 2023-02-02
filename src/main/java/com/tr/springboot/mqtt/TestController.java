package com.tr.springboot.mqtt;

import com.tr.springboot.mqtt.config.properties.MqttProperties;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author taorun
 * @date 2023/2/2 10:54
 */
@RestController
public class TestController {

    @Resource
    private MqttProperties mqttProperties;

    @Resource
    private MqttClient mqttClient;

    /**
     * 发布消息
     */
    @GetMapping("/publish/{message}")
    public void publish(@PathVariable String message) throws MqttException {
        // mqttClient 原生 publish 方法
        mqttClient.publish(mqttProperties.getDefaultTopic(), message.getBytes(), mqttProperties.getDefaultQos(), mqttProperties.getRetained());
        // 自定义 publish 方法
        publish(mqttProperties.getDefaultTopic(), message, mqttProperties.getDefaultQos(), mqttProperties.getRetained());
    }

    /**
     * 客户端连接
     */
    @GetMapping("/client/connect")
    public String connect() throws MqttException {
        mqttClient.connect();
        return mqttProperties.getClientId() + " 已连接";
    }

    /**
     * 客户端断开连接
     */
    @GetMapping("/client/disconnect")
    public String disconnect() throws MqttException {
        mqttClient.disconnect();
        return mqttProperties.getClientId() + " 已断开";
    }

    /**
     * 发布消息
     */
    public void publish(String topic, String message, int qos, boolean retained) {
        MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setQos(qos);
        mqttMessage.setRetained(retained);
        mqttMessage.setPayload(message.getBytes());
        // 主题的目的地，用于发布/订阅信息
        MqttTopic mqttTopic = mqttClient.getTopic(topic);
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

}
