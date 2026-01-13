package com.example.telemetryService.webSocket;


import com.example.telemetryService.dto.TelemetryDTO;
import com.example.telemetryService.mqtt.MqttSubscriber;
import com.example.telemetryService.security.JwtUtil;
import com.example.telemetryService.services.DeviceService;
import com.example.telemetryService.services.TelemetryParser;
import com.example.telemetryService.services.TelemetryValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.List;

@Component
public class TelemetryWebSocketHandler extends TextWebSocketHandler {

    @Autowired
    private WebSocketSessionManager sessionManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private TelemetryParser parser;

    @Autowired
    private TelemetryValidator validator;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private MqttSubscriber mqttSubscriber;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        String query = session.getUri().getQuery();
        if (query == null || !query.startsWith("token=")) {
            session.close();
            return;
        }

        String token = query.substring("token=".length());
        String email = jwtUtil.extractUsername(token);

        if (email == null) {
            session.close();
            return;
        }

        session.getAttributes().put("email", email);
        sessionManager.addSession(email, session);

        session.sendMessage(new TextMessage(
                "{\"status\":\"connected\", \"email\":\"" + email + "\"}"
        ));

        System.out.println("WebSocket connected: " + email);
    }




    public void process(String topic, String payload) {
        try {
            TelemetryDTO dto = parser.parse(payload);
            validator.validate(dto);

            String email = deviceService.getUserEmailByDevice(dto.getDeviceId());
            String json = parser.toJson(dto);

            sessionManager.sendToUser(email, json);

        } catch (Exception e) {
            System.out.println("Telemetry error: " + e.getMessage());
        }
    }



    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessionManager.removeSession(session);
        System.out.println("WebSocket Closed: " + session.getId());
    }
}
