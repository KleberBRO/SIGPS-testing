// src/main/java/com/ufrpe/sigps/service/S3StorageService.java
package com.ufrpe.sigps.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.io.InputStream;
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

        try (InputStream inputStream = file.getInputStream()) {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(uniqueFileName)
                    .contentType(file.getContentType())
                    .build();

            // Usa o inputStream que será fechado automaticamente
            s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(inputStream, file.getSize()));

            // Constrói e retorna a URL pública do objeto
            return String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, awsRegion, uniqueFileName);

        } catch (IOException e) {
            throw new RuntimeException("Falha ao fazer upload do arquivo para o S3", e);
        }
    }

    public void deleteFile(String fileUrl) {
        if (fileUrl == null || fileUrl.isBlank()) {
            return;
        }
        try {
            // Extrai a chave do arquivo da URL completa
            String fileKey = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);

            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileKey)
                    .build();

            s3Client.deleteObject(deleteObjectRequest);
            System.out.println("Arquivo deletado com sucesso do S3: " + fileKey);
        } catch (Exception e) {
            // Loga a exceção mas não impede o fluxo principal de tratamento de erro
            System.err.println("Erro ao deletar arquivo do S3: " + fileUrl);
            e.printStackTrace();
        }
    }
}