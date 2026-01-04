package com.srimega.iot.service;

import com.srimega.iot.dto.DeviceRequest;
import com.srimega.iot.dto.DeviceResponse;
import com.srimega.iot.entity.Device;
import com.srimega.iot.repository.DeviceRepository;
import org.springframework.stereotype.Service;

@Service
public class DeviceService {

    private final DeviceRepository deviceRepository;

    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public DeviceResponse registerDevice(DeviceRequest request) {

        deviceRepository.findByDeviceId(request.getDeviceId())
                .ifPresent(d -> {
                    throw new RuntimeException("Device already exists");
                });

        Device device = Device.builder()
                .deviceId(request.getDeviceId())
                .name(request.getName())
                .location(request.getLocation())
                .status(request.getStatus())
                .build();

        Device saved = deviceRepository.save(device);

        return DeviceResponse.builder()
                .id(saved.getId())
                .deviceId(saved.getDeviceId())
                .name(saved.getName())
                .location(saved.getLocation())
                .status(saved.getStatus())
                .createdAt(saved.getCreatedAt())
                .build();
    }
}
