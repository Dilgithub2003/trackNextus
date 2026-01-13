package com.example.gps_tracking_service.repository;

import com.example.gps_tracking_service.entity.DeviceInformation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceInformationRepo extends JpaRepository<DeviceInformation, Integer> {
    DeviceInformation findByDeviceID(String deviceID);

    DeviceInformation findByUserEmail(String userEmail);

}
