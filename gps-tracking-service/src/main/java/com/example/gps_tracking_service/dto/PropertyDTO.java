package com.example.gps_tracking_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PropertyDTO {
    private int deviceID;
    private int speedLimit;
    private boolean accidentState;
    private boolean motion;
}
