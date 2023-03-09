package com.tr.springboot.mqtt.config.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: TR
 * @Date: 2023/3/9
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MqttMsg {

    private String message;

    private String topic;

    private Integer qos = 0;

    private Boolean retained = false;

    public MqttMsg(String message, String topic) {
        this.message = message;
        this.topic = topic;
    }

}
