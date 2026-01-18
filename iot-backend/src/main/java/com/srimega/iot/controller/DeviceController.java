package com.srimega.iot.controller;

import com.srimega.iot.dto.*;
import com.srimega.iot.service.DeviceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/devices")
@RequiredArgsConstructor
public class DeviceController {

    private final DeviceService deviceService;

    @PostMapping
    public DeviceResponse register(@Valid @RequestBody DeviceRequest request) {
        return deviceService.register(request);
    }

    @GetMapping
    public Page<DeviceResponse> getDevices(Pageable pageable) {
        return deviceService.getDevices(pageable);
    }

    @GetMapping("/{id}")
    public DeviceResponse getById(@PathVariable Long id) {
        return deviceService.getById(id);
    }

    @GetMapping("/dashboard/{deviceId}")
    public DeviceDashboardResponse dashboard(@PathVariable String deviceId) {
        return deviceService.getDashboard(deviceId);
    }
}

