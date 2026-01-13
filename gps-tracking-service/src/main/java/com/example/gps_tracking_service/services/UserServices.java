package com.example.gps_tracking_service.services;

import com.example.gps_tracking_service.dto.DeviceAddingDTO;
import com.example.gps_tracking_service.dto.DeviceRegisterDTO;
import com.example.gps_tracking_service.dto.PropertyDTO;
import com.example.gps_tracking_service.entity.DeviceInformation;
import com.example.gps_tracking_service.entity.DeviceProperties;
import com.example.gps_tracking_service.repository.DeviceInformationRepo;
import com.example.gps_tracking_service.repository.DevicePropertyRepo;
import com.example.gps_tracking_service.security.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Transactional

public class UserServices {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private DevicePropertyRepo devicePropertyRepo;

    @Autowired
    private DeviceInformationRepo deviceInformationRepo;

    @Autowired
    private  JwtUtil jwtUtil;

    public ResponseEntity<?> savePropertiesByToken(
            PropertyDTO propertyDTO,
            HttpServletRequest request
    ) {

        // 1. Extract Authorization header
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Missing or invalid Authorization header");
        }

        // 2. Extract token
        String token = authHeader.substring(7);

        // 3. Extract user email from JWT
        String userEmail = jwtUtil.extractUsername(token);

        if (userEmail == null) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid token");
        }

        // 4. Find device registered to this user
        DeviceInformation deviceInformation =
                deviceInformationRepo.findByUserEmail(userEmail);

        if (deviceInformation == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("No device registered for this user");
        }

        if (!deviceInformation.isActive()) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("Device is inactive");
        }

        // 5. Find existing device properties
        DeviceProperties deviceProperties =
                devicePropertyRepo.findByDeviceID(deviceInformation.getDeviceID());

        if (deviceProperties == null) {
            // Create new properties if not exist
            deviceProperties = new DeviceProperties();
            deviceProperties.setDeviceID(deviceInformation.getDeviceID());
        }

        // 6. Update fields
        deviceProperties.setSpeedLimit(propertyDTO.getSpeedLimit());
        deviceProperties.setAccidentState(propertyDTO.isAccidentState());
        deviceProperties.setMotion(propertyDTO.isMotion());

        devicePropertyRepo.save(deviceProperties);

        return ResponseEntity.ok("Device properties saved successfully");
    }


    public String addDevice(DeviceAddingDTO deviceAddingDTO){

        DeviceInformation deviceInformation = deviceInformationRepo.findByDeviceID(String.valueOf(deviceAddingDTO.getDeviceID()));
        if(deviceInformation != null){
            return "This device already exist";
        }

        DeviceInformation newDeviceInformation = new DeviceInformation();
        newDeviceInformation.setDeviceID(String.valueOf(deviceAddingDTO.getDeviceID()));
        newDeviceInformation.setSecurityKey(deviceAddingDTO.getSecurityKey());
        newDeviceInformation.setImei(deviceAddingDTO.getImei());
        newDeviceInformation.setSecretKey(deviceAddingDTO.getSecretKey());

        deviceInformationRepo.save(newDeviceInformation);

        return "Device Registered Successfully";
    }

    public String registerDevice(DeviceRegisterDTO deviceRegisterDTO, HttpServletRequest httpRequest){

//        String authHeader = httpRequest.getHeader("Authorization");
//        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//            return "Missing or invalid token!";
//        }

        // Extract Token
//        String token = authHeader.substring(7);

        // Decode Token → Get Email or User ID
        String userEmail = deviceRegisterDTO.getEmail();
//        if(userEmail == null){
//            return "Unauthorized";
//        }

        DeviceInformation deviceInformation = deviceInformationRepo.findByDeviceID(String.valueOf(deviceRegisterDTO.getDeviceID()));
        if(deviceInformation == null){
            return "Invalid device ID or security key";
        }

        if (deviceInformation.getUserEmail() != null) {
            return "This device is already registered to another account.";
        }

        if(!deviceInformation.getSecurityKey().equals(deviceRegisterDTO.getSecurityKey())){
            return "Invalid device ID or security key";
        }


        deviceInformation.setActive(true);
        deviceInformation.setUserEmail(userEmail);

        deviceInformationRepo.save(deviceInformation);

        return "Device Registered Successfully";
    }
// get settings by token
    public PropertyDTO getDeviceSettingsByToken(HttpServletRequest request) {

        // 1. Read Authorization header
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Missing or invalid Authorization header");
        }

        // 2. Extract token
        String token = authHeader.substring(7);

        // 3. Extract user email from token
        String userEmail = jwtUtil.extractUsername(token);

        if (userEmail == null) {
            throw new RuntimeException("Invalid token");
        }

        // 4. Find device registered to this user
        DeviceInformation deviceInformation =
                deviceInformationRepo.findByUserEmail(userEmail);

        if (deviceInformation == null) {
            throw new RuntimeException("No device registered for this user");
        }

        if (!deviceInformation.isActive()) {
            throw new RuntimeException("Device is inactive");
        }

        // 5. Find device properties
        DeviceProperties deviceProperties =
                devicePropertyRepo.findByDeviceID(deviceInformation.getDeviceID());

        if (deviceProperties == null) {
            throw new RuntimeException("Device settings not found");
        }

        // 6. Map entity → DTO
        return modelMapper.map(deviceProperties, PropertyDTO.class);
    }


}
