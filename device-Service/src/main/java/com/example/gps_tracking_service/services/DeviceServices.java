package com.example.gps_tracking_service.services;

import com.example.gps_tracking_service.dto.DeviceRequest;
import com.example.gps_tracking_service.dto.PropertyDTO;
import com.example.gps_tracking_service.entity.DeviceInformation;
import com.example.gps_tracking_service.entity.DeviceProperties;
import com.example.gps_tracking_service.repository.DeviceInformationRepo;
import com.example.gps_tracking_service.repository.DevicePropertyRepo;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.text.DecimalFormat;
import java.util.Map;

import static java.lang.Integer.parseInt;

@Service
@Transactional

public class DeviceServices {
    @Autowired
    private DevicePropertyRepo devicePropertyRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private DeviceInformationRepo deviceInformationRepo;

    @PostMapping("/configdevice/fetchsettings")
    public ResponseEntity<?> configDevice(@RequestBody DeviceRequest deviceRequest) {

        if (deviceRequest == null || deviceRequest.getDeviceId()  == 0) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("message", "Device request is required"));
        }

        DeviceInformation deviceInformation =
                deviceInformationRepo.findByDeviceID(
                        String.valueOf(deviceRequest.getDeviceId())
                );

        if (deviceInformation == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Device not found"));
        }

        if (!deviceInformation.isActive()) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(Map.of("message", "Device is inactive"));
        }

        DeviceProperties deviceProperties =
                devicePropertyRepo.findByDeviceID(
                        String.valueOf(deviceRequest.getDeviceId())
                );

        if (deviceProperties == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Device settings not found"));
        }

        PropertyDTO response =
                modelMapper.map(deviceProperties, PropertyDTO.class);

        return ResponseEntity.ok(response);
    }


}
