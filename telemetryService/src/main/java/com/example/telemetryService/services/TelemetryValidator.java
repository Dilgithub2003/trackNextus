package com.example.telemetryService.services;

import com.example.telemetryService.dto.TelemetryDTO;
import org.springframework.stereotype.Service;

@Service
public class TelemetryValidator {

    public void validate(TelemetryDTO dto) {

        if (dto.getDeviceId() == null || dto.getDeviceId().toString().isBlank()) {
            throw new IllegalArgumentException("Missing deviceId");
        }

        if (dto.getLatitude() < -90 || dto.getLatitude() > 90) {
            throw new IllegalArgumentException("Invalid latitude");
        }

        if (dto.getLongitude() < -180 || dto.getLongitude() > 180) {
            throw new IllegalArgumentException("Invalid longitude");
        }

        if (dto.getSpeed() < 0) {
            throw new IllegalArgumentException("Invalid speed");
        }


    }
}
