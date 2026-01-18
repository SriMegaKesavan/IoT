package com.srimega.iot.controller;

import com.srimega.iot.dto.TelemetryResponse;
import com.srimega.iot.entity.DeviceTelemetry;
import com.srimega.iot.service.TelemetryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/telemetry")
@RequiredArgsConstructor
public class TelemetryController {

    private final TelemetryService telemetryService;


    // 2️⃣ Latest telemetry
    @GetMapping("/{deviceId}/latest")
    public TelemetryResponse getLatest(@PathVariable String deviceId) {
        return telemetryService.getLatest(deviceId);
    }

    // 1️⃣ All telemetry of a device
    @GetMapping("/{deviceId}")
    public List<TelemetryResponse> getByDevice(@PathVariable String deviceId) {
        return telemetryService.getByDevice(deviceId);
    }
}
