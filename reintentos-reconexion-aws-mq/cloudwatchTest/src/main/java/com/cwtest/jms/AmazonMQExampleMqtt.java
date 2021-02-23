package com.cwtest.jms;

import com.cwtest.model.TrazaDesconexion;
import com.cwtest.util.CwLogger;
import org.eclipse.paho.client.mqttv3.*;

import java.sql.SQLException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class AmazonMQExampleMqtt implements MqttCallback {
    private final static String WIRE_LEVEL_ENDPOINT =
            "wss://b-5b033529-dd29-4cd5-8950-031a97339ade-1.mq.us-west-2.amazonaws.com:61619";
    private final static String ACTIVE_MQ_USERNAME = "admin";
    private final static String ACTIVE_MQ_PASSWORD = "MySuperSecurePassword";
    private final static String ACTIVE_MQ_TOPIC = "proceso1";

    public static void main(String[] args) throws Exception {
        new AmazonMQExampleMqtt().run();
    }

    public static void run() throws MqttException, InterruptedException {

        final String clientId = "abc123";
        final MqttClient client = new MqttClient(WIRE_LEVEL_ENDPOINT, clientId);
        final MqttConnectOptions connOpts = new MqttConnectOptions();
        connOpts.setUserName(ACTIVE_MQ_USERNAME);
        connOpts.setPassword(ACTIVE_MQ_PASSWORD.toCharArray());
        client.connect(connOpts);

        CountDownLatch receivedSignal = new CountDownLatch(50);
        client.subscribe(ACTIVE_MQ_TOPIC, (tpc, msg) -> {
            procesarMsg(msg);
            receivedSignal.countDown();
        });
        receivedSignal.await(1, TimeUnit.MINUTES);

        client.disconnect();
    }

    private static void procesarMsg(MqttMessage msg) {
        try {
            throw new SQLException("No se pudo conectar a la bd!");
        } catch (Exception e) {
            // insertar traza log en cw
            //...
            // insertar evento en cw
            CwLogger.reportarEvento(new TrazaDesconexion("DESCONEXION", new String(msg.getPayload()), ACTIVE_MQ_TOPIC));
        }
    }

    @Override
    public void connectionLost(Throwable cause) {
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws MqttException {
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
    }
}
