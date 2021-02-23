package com.cwtest.service;

import com.cwtest.model.TrazaDesconexion;
import com.cwtest.util.CwLogger;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class MessagingService {
    @Autowired
    private MqttClient mqttClient;

    public void subscribe(final String topic) throws MqttException, InterruptedException {

        mqttClient.subscribeWithResponse(topic, (tpic, msg) -> {
            System.out.println("llega msg: -> " + new String(msg.getPayload()));

            try {
                throw new SQLException("No se pudo conectar a la bd!");
            } catch (Exception e) {
                System.out.println("excepcion!");
                // insertar traza log en cw
                //...
                // insertar evento en cw
                CwLogger.reportarEvento(new TrazaDesconexion("DESCONEXION", new String(msg.getPayload()), "proceso1"));
            }
        });
    }
}