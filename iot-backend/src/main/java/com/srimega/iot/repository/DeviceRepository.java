package com.srimega.iot.repository;

import com.srimega.iot.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeviceRepository extends JpaRepository<Device, Long> {
    Optional<Device> findByDeviceId(String deviceId);
}
