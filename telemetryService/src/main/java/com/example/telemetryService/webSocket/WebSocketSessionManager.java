package com.example.telemetryService.webSocket;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebSocketSessionManager {

    private final Map<String, Set<WebSocketSession>> userSessions = new ConcurrentHashMap<>();

    public void addSession(String email, WebSocketSession session) {
        userSessions.computeIfAbsent(email, k -> ConcurrentHashMap.newKeySet()).add(session);
    }

    public void removeSession(WebSocketSession session) {
        userSessions.values().forEach(set -> set.remove(session));
    }

    public void sendToUser(String email, String message) throws IOException {
        Set<WebSocketSession> sessions = userSessions.get(email);

        if (sessions != null) {
            for (WebSocketSession session : sessions) {
                if (session.isOpen()) {
                    session.sendMessage(new TextMessage(message));
                }
            }
        }else {
            System.out.println("User not online");
        }
    }
}
