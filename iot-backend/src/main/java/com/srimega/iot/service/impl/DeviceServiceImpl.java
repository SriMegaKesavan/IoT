package com.srimega.iot.service.impl;

import com.srimega.iot.dto.*;
import com.srimega.iot.entity.Device;
import com.srimega.iot.model.DeviceStatus;
import com.srimega.iot.repository.DeviceRepository;
import com.srimega.iot.repository.DeviceTelemetryRepository;
import com.srimega.iot.service.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeviceServiceImpl implements DeviceService {

    private final DeviceRepository repository;
    private final DeviceTelemetryRepository telemetryRepository;

    @Override
    public DeviceResponse register(DeviceRequest request) {
        Device device = Device.builder()
                .deviceId(request.getDeviceId())
                .name(request.getName())
                .location(request.getLocation())
                .status(request.getStatus())
                .build();

        Device saved = repository.save(device);

        return map(saved);
    }

    @Override
    public DeviceResponse updateStatus(DeviceRequest request) {

        Device device = repository.findByDeviceId(request.getDeviceId())
                .orElseThrow(() ->
                        new RuntimeException("Device not found: " + request.getDeviceId()));

        device.setStatus(request.getStatus());

        return map(repository.save(device));
    }

    @Override
    public List<DeviceResponse> getAll() {
        return repository.findAll().stream()
                .map(this::map)
                .toList();
    }

    @Override
    public DeviceResponse getById(Long id) {
        Device device = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Device not found"));
        return map(device);
    }

    private void validateTransition(DeviceStatus current, DeviceStatus next) {

        if (current == DeviceStatus.DECOMMISSIONED) {
            throw new IllegalStateException("Decommissioned device cannot change state");
        }

        if (current == DeviceStatus.NEW && next != DeviceStatus.ACTIVE) {
            throw new IllegalStateException("NEW device can only move to ACTIVE");
        }
    }

    @Override
    public DeviceDashboardResponse getDashboard(String deviceId) {

        Device device = repository.findByDeviceId(deviceId)
                .orElseThrow(() -> new RuntimeException("Device not found"));

        return telemetryRepository
                .findTopByDeviceIdOrderByRecordedAtDesc(deviceId)
                .map(t -> DeviceDashboardResponse.builder()
                        .deviceId(device.getDeviceId())
                        .name(device.getName())
                        .location(device.getLocation())
                        .status(device.getStatus())
                        .temperature(t.getTemperature())
                        .humidity(t.getHumidity())
                        .battery(t.getBattery())
                        .health(calculateHealth(t.getBattery()))
                        .lastUpdated(t.getRecordedAt())
                        .build())
                .orElse(DeviceDashboardResponse.builder()
                        .deviceId(device.getDeviceId())
                        .name(device.getName())
                        .location(device.getLocation())
                        .status(device.getStatus())
                        .health("NO_DATA")
                        .build());
    }

    private String calculateHealth(Integer battery) {
        if (battery == null) return "UNKNOWN";
        if (battery < 20) return "CRITICAL";
        if (battery < 50) return "LOW";
        return "HEALTHY";
    }

    private DeviceResponse map(Device device) {
        return DeviceResponse.builder()
                .id(device.getId())
                .deviceId(device.getDeviceId())
                .name(device.getName())
                .location(device.getLocation())
                .status(device.getStatus())
                .createdAt(device.getCreatedAt())
                .build();
    }

    @Override
    public Page<DeviceResponse> getDevices(Pageable pageable) {
        return repository
                .findAll(pageable)
                .map(this::toResponse);
    }

    private DeviceResponse toResponse(Device device) {
        return DeviceResponse.builder()
                .deviceId(device.getDeviceId())
                .name(device.getName())
                .location(device.getLocation())
                .status(device.getStatus())
                .build();
    }
}
