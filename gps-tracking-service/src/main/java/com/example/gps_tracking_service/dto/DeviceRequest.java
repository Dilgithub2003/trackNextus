package com.example.gps_tracking_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class DeviceRequest {
    private int deviceId;
    private String secretKey;
}
