package com.srimega.iot.repository;

import com.srimega.iot.entity.DeviceTelemetry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DeviceTelemetryRepository
        extends JpaRepository<DeviceTelemetry, Long> {

    List<DeviceTelemetry> findByDeviceIdOrderByRecordedAtDesc(String deviceId);

    Optional<DeviceTelemetry> findFirstByDeviceIdOrderByRecordedAtDesc(String deviceId);

    Optional<DeviceTelemetry> findTopByDeviceIdOrderByRecordedAtDesc(String deviceId);
}
