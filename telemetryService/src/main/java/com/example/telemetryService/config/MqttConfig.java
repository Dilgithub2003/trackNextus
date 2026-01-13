package com.example.telemetryService.config;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.*;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;

@Configuration
public class MqttConfig {

    @Value("${mqtt.broker}")
    private String brokerUrl;

    @Value("${mqtt.clientId}")
    private String clientId;

    @Value("${mqtt.username}")
    private String username;

    @Value("${mqtt.password}")
    private String password;

    @Bean
    public MqttClient mqttClient() throws Exception {

        MqttClient client = new MqttClient(brokerUrl, clientId, new MemoryPersistence());

        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(true);
        options.setAutomaticReconnect(true);
        options.setUserName(username);
        options.setPassword(password.toCharArray());

        // SSL only for mqtts://
        if (brokerUrl.startsWith("mqtts://")) {
            SSLContext sslContext = createSSLContext();
            options.setSocketFactory(sslContext.getSocketFactory());
        }

        client.connect(options);
        System.out.println(" Connected securely to MQTT broker.");
        return client;
    }

    private SSLContext createSSLContext() throws Exception {
        // Load EMQX CA Cert from resources
        InputStream caInput = getClass().getClassLoader()
                .getResourceAsStream("certs/emqx-ca.crt");

        if (caInput == null) {
            throw new IllegalArgumentException("CA Certificate file not found!");
        }

        // Load CA into KeyStore
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(null, null); // empty keystore
        keyStore.setCertificateEntry("emqx-ca",
                java.security.cert.CertificateFactory
                        .getInstance("X.509")
                        .generateCertificate(caInput)
        );

        // Create TrustManager from keystore
        TrustManagerFactory tmf =
                TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(keyStore);

        // Create SSLContext
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, tmf.getTrustManagers(), new SecureRandom());

        return sslContext;
    }
}
