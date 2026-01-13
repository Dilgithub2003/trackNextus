package com.example.gps_tracking_service.Controller;

import com.example.gps_tracking_service.dto.DeviceRequest;
import com.example.gps_tracking_service.dto.PropertyDTO;
import com.example.gps_tracking_service.entity.DeviceProperties;
import com.example.gps_tracking_service.services.DeviceServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/configdevice")


public class DeviceController {

    @Autowired
    private DeviceServices deviceServices;

    @PostMapping("/fetchsettings")
    public ResponseEntity fetchsettings(@RequestBody DeviceRequest deviceRequest){
            return deviceServices.configDevice(deviceRequest);
    }

}
