// src/main/java/com/ufrpe/sigps/service/S3StorageService.java
package com.ufrpe.sigps.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.IOException;
import java.util.UUID;

@Service
public class S3StorageService {

    private final S3Client s3Client;
    private final String bucketName;
    private final String awsRegion;

    public S3StorageService(S3Client s3Client,
                            @Value("${aws.s3.bucket-name}") String bucketName,
                            @Value("${aws.region}") String awsRegion) {
        this.s3Client = s3Client;
        this.bucketName = bucketName;
        this.awsRegion = awsRegion;
    }

    public String uploadFile(MultipartFile file) {
        if (file == null || file.isEmpty() || file.getOriginalFilename() == null) {
            throw new RuntimeException("Arquivo inválido ou não selecionado.");
        }

        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
        String uniqueFileName = UUID.randomUUID().toString() + "_" + originalFileName;

        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(uniqueFileName)
                    .contentType(file.getContentType())
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

            // Constrói e retorna a URL pública do objeto
            return String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, awsRegion, uniqueFileName);

        } catch (IOException e) {
            throw new RuntimeException("Falha ao fazer upload do arquivo para o S3", e);
        }
    }
}