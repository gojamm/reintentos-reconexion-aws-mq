package com.cwtest;

import com.cwtest.service.MessagingService;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Cwtest  implements CommandLineRunner {

    @Autowired
    private MessagingService messagingService;

    @Autowired
    private ConfigurableApplicationContext context;

    public static void main(String[] args) {
        SpringApplication.run(Cwtest.class, args);
        //AmazonMQExampleMqtt.run();
    }

    @Override
    public void run(String... args) throws Exception {

        messagingService.subscribe("proceso1");
        context.close();
    }

}
