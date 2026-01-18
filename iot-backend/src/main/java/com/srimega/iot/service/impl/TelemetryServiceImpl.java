package com.srimega.iot.service.impl;

import com.srimega.iot.dto.TelemetryRequest;
import com.srimega.iot.dto.TelemetryResponse;
import com.srimega.iot.entity.DeviceTelemetry;
import com.srimega.iot.repository.DeviceTelemetryRepository;
import com.srimega.iot.service.TelemetryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TelemetryServiceImpl implements TelemetryService {

    private final DeviceTelemetryRepository repository;

    @Override
    public void save(TelemetryRequest request) {
        DeviceTelemetry telemetry = DeviceTelemetry.builder()
                .deviceId(request.getDeviceId())
                .temperature(request.getTemperature())
                .humidity(request.getHumidity())
                .battery(request.getBattery())
                .recordedAt(LocalDateTime.now())
                .build();

        repository.save(telemetry);
    }

    @Override
    public List<TelemetryResponse> getByDevice(String deviceId) {
        return repository.findByDeviceIdOrderByRecordedAtDesc(deviceId)
                .stream()
                .map(this::map)
                .toList();
    }

    @Override
    public TelemetryResponse getLatest(String deviceId) {
        DeviceTelemetry telemetry = repository
                .findFirstByDeviceIdOrderByRecordedAtDesc(deviceId)
                .orElseThrow(() -> new RuntimeException("No telemetry found"));

        return map(telemetry);
    }

    private TelemetryResponse map(DeviceTelemetry t) {
        return TelemetryResponse.builder()
                .id(t.getId())
                .deviceId(t.getDeviceId())
                .temperature(t.getTemperature())
                .humidity(t.getHumidity())
                .battery(t.getBattery())
                .recordedAt(t.getRecordedAt())
                .build();
    }
}
