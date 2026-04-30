package com.srimega.iot.ai;

import com.srimega.iot.dto.DeviceDashboardResponse;
import com.srimega.iot.entity.Device;
import com.srimega.iot.repository.DeviceRepository;
import com.srimega.iot.repository.DeviceTelemetryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class IoTContextProvider {

    private final DeviceRepository deviceRepository;
    private final DeviceTelemetryRepository telemetryRepository;

    public String buildContext() {
        List<Device> devices = deviceRepository.findAll();

        if (devices.isEmpty()) {
            return "No devices are registered in the system yet.";
        }

        String deviceSummaries = devices.stream()
                .map(this::toDeviceContext)
                .reduce((left, right) -> left + "\n" + right)
                .orElse("No device context available.");

        return """
        IoT system summary:
        Total devices: %d

        Device details:
        %s
        """.formatted(devices.size(), deviceSummaries);
    }

    private String toDeviceContext(Device device) {
        DeviceDashboardResponse dashboard = telemetryRepository
                .findTopByDeviceIdOrderByRecordedAtDesc(device.getDeviceId())
                .map(telemetry -> DeviceDashboardResponse.builder()
                        .deviceId(device.getDeviceId())
                        .name(device.getName())
                        .location(device.getLocation())
                        .status(device.getStatus())
                        .temperature(telemetry.getTemperature())
                        .humidity(telemetry.getHumidity())
                        .battery(telemetry.getBattery())
                        .health(calculateHealth(telemetry.getBattery()))
                        .lastUpdated(telemetry.getRecordedAt())
                        .build())
                .orElse(DeviceDashboardResponse.builder()
                        .deviceId(device.getDeviceId())
                        .name(device.getName())
                        .location(device.getLocation())
                        .status(device.getStatus())
                        .health("NO_DATA")
                        .build());

        return """
        - Device ID: %s
          Name: %s
          Location: %s
          Status: %s
          Temperature: %s
          Humidity: %s
          Battery: %s
          Health: %s
          Last Updated: %s
        """.formatted(
                dashboard.getDeviceId(),
                safeValue(dashboard.getName()),
                safeValue(dashboard.getLocation()),
                safeValue(dashboard.getStatus()),
                safeValue(dashboard.getTemperature()),
                safeValue(dashboard.getHumidity()),
                safeValue(dashboard.getBattery()),
                safeValue(dashboard.getHealth()),
                safeValue(dashboard.getLastUpdated())
        );
    }

    private String calculateHealth(Integer battery) {
        if (battery == null) {
            return "UNKNOWN";
        }
        if (battery < 20) {
            return "CRITICAL";
        }
        if (battery < 50) {
            return "LOW";
        }
        return "HEALTHY";
    }

    private String safeValue(Object value) {
        return value == null ? "N/A" : value.toString();
    }
}
