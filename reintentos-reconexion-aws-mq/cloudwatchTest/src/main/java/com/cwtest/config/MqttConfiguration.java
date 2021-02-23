package com.cwtest.config;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class MqttConfiguration {

    @Autowired
    Environment env;

    @Bean
    @ConfigurationProperties(prefix = "mqtt")
    public MqttConnectOptions mqttConnectOptions() {

        MqttConnectOptions conf = new MqttConnectOptions();
        conf.setUserName(env.getProperty("mqtt.admin"));
        conf.setPassword(env.getProperty("mqtt.password").toCharArray());
        return conf;
    }

    @Bean
    public MqttClient mqttClient(@Value("${mqtt.clientId}") String clientId,
                                  @Value("${mqtt.hostname}") String hostname, @Value("${mqtt.port}") int port) throws MqttException {

        MqttClient mqttClient = new MqttClient("wss://" + hostname + ":" + port, clientId+Math.random());

        MqttConnectOptions conf = new MqttConnectOptions();
        conf.setUserName(env.getProperty("mqtt.user"));
        conf.setPassword(env.getProperty("mqtt.password").toCharArray());

        mqttClient.connect(conf);

        return mqttClient;
    }


}
