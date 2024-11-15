package com.nlo.minio;

import io.minio.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.net.URLDecoder;

@Slf4j
@Service
@RequiredArgsConstructor
public class MinioStorageService {
    private final MinioClient minioClient;

    @Value("${minio.bucket-name}")
    private String bucketName;

    public String saveFile(MultipartFile file, String id, String folderName) {
        try {
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }

            // Create the full object name with folder structure
            String objectName = folderName + "/" + id + "/" + file.getOriginalFilename();

            minioClient.putObject(PutObjectArgs.builder().bucket(bucketName).object(objectName).stream(file.getInputStream(), file.getSize(), -1).build());
            return objectName;
        } catch (Exception e) {
            log.error(ExceptionUtils.getStackTrace(e));
        }
        return null;
    }

    public ResponseEntity<?> download(String path) {
        try {
            path = URLDecoder.decode(path, "UTF-8");
            InputStream stream = minioClient.getObject(GetObjectArgs.builder().bucket(bucketName).object(path).build());

            // Get the object metadata to determine content length and type
            StatObjectResponse stat = minioClient.statObject(StatObjectArgs.builder().bucket(bucketName).object(path).build());

            // Prepare headers for the response
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + path + "\"");
            headers.add(HttpHeaders.CONTENT_TYPE, stat.contentType()); // Set the correct content type
            //headers.add(HttpHeaders.CONTENT_LENGTH, String.valueOf(stat.)); // Set content length

            return ResponseEntity.ok().headers(headers).body(new InputStreamResource(stream));
        } catch (Exception e) {
            log.error("Error occurred while downloading file: {}", ExceptionUtils.getStackTrace(e));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ExceptionUtils.getStackTrace(e)); // or you can return a more meaningful error response
        }
    }


}