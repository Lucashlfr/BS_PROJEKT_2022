package de.atruvia.sensormanager;

import de.atruvia.sensormanager.app.services.sql.DatabaseService;
import de.atruvia.sensormanager.mqtt.MqttClientHandler;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Manager {

    public static void main(String[] args) {

        System.out.println(new DatabaseService().getConnection().toString());

        SpringApplication.run(Manager.class, args);

        try {
            MqttClientHandler.connect();
        } catch (MqttException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
