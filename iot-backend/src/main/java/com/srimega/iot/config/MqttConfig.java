package com.srimega.iot.config;

import com.fasterxml.jackson.databind.json.JsonMapper;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqttConfig {

    @Bean
    public JsonMapper jsonMapper() {
        return JsonMapper.builder()
                .findAndAddModules()
                .build();
    }

    @Bean
    public MqttClient mqttClient() throws Exception {
        MqttClient client = new MqttClient(
                "tcp://127.0.0.1:1883",
                MqttClient.generateClientId()
        );

        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(true);
        client.connect(options);

        return client;
    }
}