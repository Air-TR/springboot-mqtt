package com.tr.springboot.mqtt.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author taorun
 * @date 2023/2/2 10:20
 */
@Component
@ConfigurationProperties(prefix = "spring.mqtt")
public class MqttProperties {

    private String url;
    private String username;
    private String password;
    private String clientId;
    private String[] topic;
    private int[] qos;
    private String defaultTopic;
    private Integer defaultQos;
    private Integer completionTimeout;
    private Boolean automaticReconnect;
    private Boolean cleanSession;
    private Boolean retained;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String[] getTopic() {
        return topic;
    }

    public void setTopic(String[] topic) {
        this.topic = topic;
    }

    public int[] getQos() {
        return qos;
    }

    public void setQos(int[] qos) {
        this.qos = qos;
    }

    public String getDefaultTopic() {
        return defaultTopic;
    }

    public void setDefaultTopic(String defaultTopic) {
        this.defaultTopic = defaultTopic;
    }

    public Integer getDefaultQos() {
        return defaultQos;
    }

    public void setDefaultQos(Integer defaultQos) {
        this.defaultQos = defaultQos;
    }

    public Integer getCompletionTimeout() {
        return completionTimeout;
    }

    public void setCompletionTimeout(Integer completionTimeout) {
        this.completionTimeout = completionTimeout;
    }

    public Boolean getAutomaticReconnect() {
        return automaticReconnect;
    }

    public void setAutomaticReconnect(Boolean automaticReconnect) {
        this.automaticReconnect = automaticReconnect;
    }

    public Boolean getCleanSession() {
        return cleanSession;
    }

    public void setCleanSession(Boolean cleanSession) {
        this.cleanSession = cleanSession;
    }

    public Boolean getRetained() {
        return retained;
    }

    public void setRetained(Boolean retained) {
        this.retained = retained;
    }

}
