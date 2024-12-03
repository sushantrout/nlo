package com.nlo.controller;

import com.nlo.service.DeviceTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/device-token")
@RequiredArgsConstructor
public class DeviceTokenController {

    private final DeviceTokenService deviceTokenService;

    @PutMapping
    public void updateToken(@RequestParam String deviceToken) {
        deviceTokenService.updateToken(deviceToken);
    }
}
