package com.nlo.service;

import com.nlo.minio.MinioStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DownloadService {
    private final MinioStorageService minioStorageService;
    public ResponseEntity<?> download(String path) {
        return minioStorageService.download(path);
    }
}
