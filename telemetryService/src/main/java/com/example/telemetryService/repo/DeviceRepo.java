package com.example.telemetryService.repo;

import com.example.telemetryService.entity.DeviceInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DeviceRepo extends JpaRepository<DeviceInformation, Integer> {
    List<DeviceInformation> findByUserEmail(String userEmail);

    @Query("SELECT d FROM DeviceInformation d WHERE d.deviceID = :deviceID")
    DeviceInformation findByDeviceID(@Param("deviceID") String deviceID);
}
