package com.fp.cloud.configuration.mqtt;

import com.fp.cloud.CloudApplication;
import com.fp.cloud.utility.SocketFactory;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

@Slf4j
@Service
public class MqttService implements IMqttService {

    private IMqttClient mqttClient;

    @Autowired
    private MqttProperties prop;

    @Override
    public boolean connect() throws MqttException {
        boolean isSsl = prop.isSsl();
        String connection = isSsl ? "ssl://" : "tcp://";
        mqttClient = new MqttClient(connection + prop.getHostname() + ":" + prop.getPort(), prop.getClientId(), new MemoryPersistence());
        if (isSsl) {
            SocketFactory.SocketFactoryOptions socketFactoryOptions = new SocketFactory.SocketFactoryOptions();
            try {
                String fileName = "raw/m2mqtt_dev_ca.crt";
                ClassLoader classLoader = new CloudApplication().getClass().getClassLoader();
                InputStream stream = new FileInputStream(new File(classLoader.getResource(fileName).getFile()));
                socketFactoryOptions.withCaInputStream(stream);
                prop.setSocketFactory(new SocketFactory(socketFactoryOptions));
            } catch (IOException | NoSuchAlgorithmException | KeyStoreException | CertificateException | KeyManagementException | UnrecoverableKeyException e) {
                log.info("Exception  " + mqttClient.getServerURI());
                log.error(e.getMessage());
                e.printStackTrace();
            }
        }
        mqttClient.connect(prop.getMqttConnectOptions());
        return mqttClient.isConnected();
    }

    @Override
    public boolean isConnected() throws MqttException {
        return mqttClient.isConnected();
    }

    @Override
    public void publish(String topic, String message) throws MqttException {
        MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setPayload(message.getBytes());
        mqttClient.publish(topic, mqttMessage);
    }

    @Override
    public void subscribe(String topic) throws MqttException {
        mqttClient.subscribeWithResponse(topic, (tpic, msg) -> {
            System.out.println(msg.getId() + " -> " + new String(msg.getPayload()));
        });
        mqttClient.subscribe(topic);
    }

    @Override
    public void unsubscribe(String topic) throws MqttException {
        mqttClient.unsubscribe(topic);
    }

    @Override
    public void disconnect() throws MqttException {
        mqttClient.disconnect();
    }

    //@Async
//    public CompletableFuture<String> findUser(String user) throws InterruptedException {
//        logger.info("Looking up " + user);
//        // Artificial delay of 1s for demonstration purposes
//        Thread.sleep(1000L);
//        return CompletableFuture.completedFuture("async running");
//    }
}
