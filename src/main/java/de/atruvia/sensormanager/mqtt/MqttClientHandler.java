package de.atruvia.sensormanager.mqtt;

import de.atruvia.sensormanager.app.entities.ValueEntity;
import de.atruvia.sensormanager.cache.Cache;
import org.eclipse.paho.client.mqttv3.*;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class MqttClientHandler {

    private static IMqttClient client;

    public static void connect() throws MqttException, InterruptedException {

        String publisherId = UUID.randomUUID().toString();
        client = new MqttClient("tcp://test.mosquitto.org:1883",publisherId);

        MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);
        options.setConnectionTimeout(10);
        client.connect(options);

        CountDownLatch receivedSignal = new CountDownLatch(10);
        client.subscribe("helfer", (topic, msg) -> {
            byte[] payload = msg.getPayload();
            // ... payload handling omitted

            String str1 = new String(payload);
            System.out.println(str1);
            save(str1);

            receivedSignal.countDown();
        });
        receivedSignal.await(5, TimeUnit.SECONDS);
    }

    private static void save(String value){
        String[] vl = value.split("/");

        String s_tmp = vl[0].replace("T","");
        String s_prs = vl[1].replace("P","");
        String s_alt = vl[2].replace("A","");

        float f_tmp = Float.parseFloat(s_tmp);
        float f_prs = Float.parseFloat(s_prs);
        float f_alt = Float.parseFloat(s_alt);

        ValueEntity valueEntity = new ValueEntity(System.currentTimeMillis(), f_tmp, f_prs, f_alt);
        System.out.println(System.currentTimeMillis());
        Cache.ENTRY_SERVICE.save(valueEntity);
    }

}
