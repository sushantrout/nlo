package com.nlo.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class S3StorageService {
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket-name}")
    private String bucketName;

    public String saveFile(MultipartFile file, String id) {
        File convertedFile = null;
        try {
            convertedFile = File.createTempFile("temp-", file.getOriginalFilename());
            file.transferTo(convertedFile);
            String keyName = "uploads/" + id + "-" + file.getOriginalFilename();
            amazonS3.putObject(new PutObjectRequest(bucketName, keyName, convertedFile));
            return amazonS3.getUrl(bucketName, keyName).toString();
        } catch (IOException e) {
            throw new RuntimeException("Error while uploading file to S3", e);
        } finally {
            // Delete the temporary file
            if (convertedFile != null && convertedFile.exists()) {
                convertedFile.delete();
            }
        }
    }
}
