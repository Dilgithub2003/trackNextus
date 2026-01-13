package com.example.gps_tracking_service.Controller;

import com.example.gps_tracking_service.dto.DeviceAddingDTO;
import com.example.gps_tracking_service.dto.DeviceRegisterDTO;
import com.example.gps_tracking_service.dto.PropertyDTO;
import com.example.gps_tracking_service.services.UserServices;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/device")

public class UserController {
    @Autowired
    public UserServices userServices;

    @PostMapping("/settings")
    public ResponseEntity<?> saveSettings( @RequestBody PropertyDTO propertyDTO, HttpServletRequest request) {
        return userServices.savePropertiesByToken(propertyDTO, request);
    }

    @PostMapping("/add")
    public  String add(@RequestBody DeviceAddingDTO deviceAddingDTO){
        return userServices.addDevice(deviceAddingDTO);
    }

    @PostMapping("/register")
    public String register(@RequestBody DeviceRegisterDTO deviceRegisterDTO, HttpServletRequest httpServletRequest){
        return userServices.registerDevice(deviceRegisterDTO, httpServletRequest);
    }

    @GetMapping("/settings")
    public ResponseEntity<?> getDeviceSettings(HttpServletRequest request) {
        try {
            PropertyDTO settings = userServices.getDeviceSettingsByToken(request);
            return ResponseEntity.ok(settings);
        } catch (RuntimeException ex) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", ex.getMessage()));
        }
    }

}
