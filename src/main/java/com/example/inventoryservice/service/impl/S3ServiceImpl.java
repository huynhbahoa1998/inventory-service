package com.example.inventoryservice.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.example.inventoryservice.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
public class S3ServiceImpl implements S3Service {
    @Autowired
    private AmazonS3 s3Client;

    @Value("${cloud.aws.s3.bucket.name}")
    private String bucketName;

    @Override
    public void uploadFile(MultipartFile file) throws IOException {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        s3Client.putObject(bucketName, file.getOriginalFilename(), file.getInputStream(), metadata);
    }

    @Override
    public InputStream getFileInputStream(String fileName) {
        S3Object s3object = s3Client.getObject(bucketName, fileName);
        InputStream inputStream = s3object.getObjectContent();
        return inputStream;
    }
}
