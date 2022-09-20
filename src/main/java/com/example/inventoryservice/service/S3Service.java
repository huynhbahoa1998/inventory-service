package com.example.inventoryservice.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public interface S3Service {

    void uploadFile(MultipartFile file) throws IOException;

    InputStream getFileInputStream(String fileName);
}
