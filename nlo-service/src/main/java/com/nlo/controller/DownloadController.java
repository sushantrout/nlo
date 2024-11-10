package com.nlo.controller;

import com.nlo.service.DownloadService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/download")
@RequiredArgsConstructor
public class DownloadController {
    private final DownloadService downloadService;

    @GetMapping(value = "/**") // Catch all paths under /api/download
    public ResponseEntity download(HttpServletRequest request) {
        // Extract the full path after /api/download/
        String fullPath = request.getRequestURI().substring(request.getContextPath().length() + "/api/download/".length());
        return downloadService.download(fullPath);
    }
}

