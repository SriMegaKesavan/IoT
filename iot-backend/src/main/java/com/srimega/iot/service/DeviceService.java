package com.srimega.iot.service;

import com.srimega.iot.dto.DeviceDashboardResponse;
import com.srimega.iot.dto.DeviceRequest;
import com.srimega.iot.dto.DeviceResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DeviceService {
    DeviceResponse register(DeviceRequest request);
    List<DeviceResponse> getAll();
    DeviceResponse getById(Long id);
    DeviceResponse updateStatus(DeviceRequest request);
    DeviceDashboardResponse getDashboard(String deviceId);
    Page<DeviceResponse> getDevices(Pageable pageable);
}
