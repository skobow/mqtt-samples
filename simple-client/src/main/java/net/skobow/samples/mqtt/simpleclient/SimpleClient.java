package net.skobow.samples.mqtt.simpleclient;

import com.hivemq.client.mqtt.MqttClient;
import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.mqtt3.Mqtt3BlockingClient;

import java.util.UUID;

public class SimpleClient {

    private final Mqtt3BlockingClient mqttClient;

    public SimpleClient(final Mqtt3BlockingClient mqttClient) {
        this.mqttClient = mqttClient;
    }

    public void run() {
        try {
            this.mqttClient.connect();

            this.mqttClient.publishWith()
                    .topic("hello")
                    .payload("Hello World".getBytes())
                    .qos(MqttQos.AT_MOST_ONCE)
                    .send();

            this.mqttClient.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        final var mqtt3Client = MqttClient.builder()
                .identifier(UUID.randomUUID().toString())
                .serverHost("localhost")
                .serverPort(1883)
                .useMqttVersion3()
                .build();

        new SimpleClient(mqtt3Client.toBlocking()).run();
    }
}
