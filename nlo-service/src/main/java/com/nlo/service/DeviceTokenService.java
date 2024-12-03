package com.nlo.service;

import com.nlo.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class DeviceTokenService {
    private final UserService userService;

    public void updateToken(String deviceToken) {
        try {
            String id = userService.getCurrentUser().getId();
            User user = userService.findByUserId(id);
            if(!deviceToken.equals(user.getDeviceToken())) {
                user.setDeviceToken(deviceToken);
            }
            userService.saveEntity(user);
        } catch (Exception e) {

        }
    }
}
