package com.example.telemetryService.cache;

import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class DeviceOwnerCache {

    private final ConcurrentHashMap<String, String> cache = new ConcurrentHashMap<>();

    public void put(String deviceId, String email) {
        cache.put(deviceId, email);
    }

    public String get(String deviceId) {
        return cache.get(deviceId);
    }

    public boolean contains(String deviceId) {
        return cache.containsKey(deviceId);
    }
}

