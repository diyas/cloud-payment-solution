package com.fp.cloud.configuration.mqtt;

import org.eclipse.paho.client.mqttv3.MqttException;

public interface IMqttService {
    public boolean connect() throws MqttException;

    public boolean isConnected() throws MqttException;

    public void publish(String topic, String message) throws MqttException;

    public void subscribe(String topic) throws MqttException;

    public void unsubscribe(String topic) throws MqttException;

    public void disconnect() throws MqttException;

}
