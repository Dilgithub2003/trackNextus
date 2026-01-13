package com.example.gps_tracking_service.repository;

import com.example.gps_tracking_service.entity.DeviceProperties;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DevicePropertyRepo extends JpaRepository<DeviceProperties, Integer> {
    DeviceProperties findByDeviceID(String deviceID);
}


