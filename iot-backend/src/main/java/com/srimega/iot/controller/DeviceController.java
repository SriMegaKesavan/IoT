package com.srimega.iot.controller;

import com.srimega.iot.entity.Device;
import com.srimega.iot.repository.DeviceRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/devices")
public class DeviceController {

    private final DeviceRepository repository;

    public DeviceController(DeviceRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public Device register(@RequestBody Device device) {
        return repository.save(device);
    }
}

