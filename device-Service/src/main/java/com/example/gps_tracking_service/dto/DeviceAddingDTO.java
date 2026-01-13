package com.example.gps_tracking_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class DeviceAddingDTO {
    private int deviceID;
    private String securityKey;
    private int imei;
    private String secretKey;
}
