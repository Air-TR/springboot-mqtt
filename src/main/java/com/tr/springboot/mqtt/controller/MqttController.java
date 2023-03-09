package com.tr.springboot.mqtt.controller;

import com.tr.springboot.mqtt.config.entity.MqttMsg;
import com.tr.springboot.mqtt.config.service.MqttService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author taorun
 * @date 2023/1/29 8:35
 */
@RestController
public class MqttController {

    @Resource
    private MqttService mqttService;

    /**
     * 发布消息
     */
    @GetMapping("/publish/{message}")
    public void publish(@PathVariable String message) {
        // MqttClient 原生 publish 方法
        mqttService.publish(new MqttMsg(message, "topic"));
        // 自定义 publish 方法，发布消息并追踪完成
        mqttService.publishForCompletion(new MqttMsg(message, "topic"));
    }

    /**
     * 订阅主题
     */
    @GetMapping("/subscribe")
    public void subscribe() {
        mqttService.subscribe("topic5", "topic6", "topic7");
    }

    /**
     * 取消订阅主题
     */
    @GetMapping("/unsubscribe")
    public void unsubscribe() {
        mqttService.unsubscribe("topic5", "topic6");
    }

    /**
     * 客户端连接
     */
    @GetMapping("/client/connect")
    public void connect() {
        mqttService.connect();
    }

    /**
     * 客户端断开连接
     */
    @GetMapping("/client/disconnect")
    public void disconnect() {
        mqttService.disconnect();
    }

}
