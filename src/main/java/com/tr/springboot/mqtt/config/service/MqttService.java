package com.tr.springboot.mqtt.config.service;

import com.tr.springboot.mqtt.config.entity.MqttMsg;

public interface MqttService {

    /**
     * 发布消息，原生发布消息方法
     */
    void publish(MqttMsg mqttMsg);

    /**
     * 发布消息并追踪完成
     */
    void publishForCompletion(MqttMsg mqttMsg);

    /**
     * 订阅主题
     */
    void subscribe(String... topics);

    /**
     * 取消订阅主题
     */
    void unsubscribe(String... topics);

    /**
     * 客户端连接
     */
    void connect();

    /**
     * 客户端断开连接
     */
    void disconnect();

}
