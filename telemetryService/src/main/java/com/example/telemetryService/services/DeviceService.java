package com.example.telemetryService.services;

import com.example.telemetryService.cache.DeviceOwnerCache;
import com.example.telemetryService.entity.DeviceInformation;
import com.example.telemetryService.repo.DeviceRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Transactional
@Service
public class DeviceService {

    @Autowired
    private DeviceRepo deviceRepo;

    @Autowired
    private DeviceOwnerCache cache;

    public List<String> getDeviceIdsByEmail(String email) {
        return deviceRepo.findByUserEmail(email)
                .stream()
                .map(DeviceInformation::getDeviceID)
                .toList();
    }

//    public String getUserEmailByDevice(String deviceId) {
//        DeviceInformation d = deviceRepo.findByDeviceID(deviceId);
//        return d != null ? d.getUserEmail() : null;
//    }

    public String getUserEmailByDevice(String deviceId) {

        // 1️⃣ Fast path (no DB hit)
        if (cache.contains(deviceId)) {
            return cache.get(deviceId);
        }

        // 2️⃣ DB lookup only once
        DeviceInformation d = deviceRepo.findByDeviceID(deviceId);
        if (d == null || d.getUserEmail() == null) return null;

        // 3️⃣ Save in cache for next time
        cache.put(deviceId, d.getUserEmail());

        return d.getUserEmail();
    }
}
