package com.example.inventoryservice.controller;

import com.example.inventoryservice.service.S3Service;
import com.example.inventoryservice.service.SQSService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/inventory/file")
public class FileController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private S3Service s3Service;

    @Autowired
    private SQSService sqsService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            s3Service.uploadFile(file);
            sqsService.sendMessage(file.getOriginalFilename());
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("Your file has been uploaded successfully!");
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error uploading file: " + e.getMessage());
        }
    }
}
