package com.example.telemetryService.mqtt;

import com.example.telemetryService.dto.TelemetryDTO;
import com.example.telemetryService.services.DeviceService;
import com.example.telemetryService.services.TelemetryParser;
import com.example.telemetryService.services.TelemetryValidator;
import com.example.telemetryService.webSocket.WebSocketSessionManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.annotation.PostConstruct;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MqttSubscriber {

    @Autowired
    private MqttClient mqttClient;

    @Value("${mqtt.topic}")
    private String topic;

    @Autowired
    private TelemetryParser telemetryParser;

    @Autowired
    private TelemetryValidator telemetryValidator;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private WebSocketSessionManager sessionManager;

    @PostConstruct
    public void subscribe() throws MqttException {

        mqttClient.subscribe(topic, (receivedTopic, message) -> {

            String payload = new String(message.getPayload());

            try {
                TelemetryDTO dto = telemetryParser.parse(payload);
                telemetryValidator.validate(dto);

                String deviceId = dto.getDeviceId();

                // Find user email from cache or DB
                String email = deviceService.getUserEmailByDevice(deviceId);
                if (email == null) {
                    System.out.println("⚠ Unknown device: " + deviceId);
                    return;
                }

                // Convert to JSON
                String json = telemetryParser.toJson(dto);

                // Send to correct user's sessions
                sessionManager.sendToUser(email, json);

            } catch (Exception e) {
                System.out.println("Invalid payload: " + payload);
                System.out.println("Error: " + e.getMessage());
            }
        });

        System.out.println("✔ Subscribed to topic: " + topic);
    }
}
