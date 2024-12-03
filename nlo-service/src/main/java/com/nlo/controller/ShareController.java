package com.nlo.controller;

import com.nlo.constant.ShareType;
import com.nlo.service.ShareService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/share")
@RequiredArgsConstructor
public class ShareController {
    private final ShareService shareService;

    @PutMapping("{type}/{objectId}")
    public void share(@PathVariable ShareType type, @PathVariable String objectId) {
        shareService.increseShare(type, objectId);
    }
}
