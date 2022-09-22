package com.example.inventoryservice.listener;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.event.S3EventNotification;
import com.amazonaws.services.s3.model.S3Object;
import com.example.inventoryservice.model.Inventory;
import com.example.inventoryservice.service.DynamoDBService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class SQSListener {

    @Autowired
    private AmazonS3 s3Client;

    @Value("${cloud.aws.s3.bucket.name}")
    private String bucketName;

    @Autowired
    private DynamoDBService dynamoDBService;

    private static final String DELIMITER = ",";

    private static final int UUID_INDEX = 0;

    private static final int BOOK_UUID_INDEX = 1;

    private static final int QUANTITY_INDEX = 2;

    @SqsListener(value = "${cloud.aws.sqs.queue.name}", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void receiveMessage(S3EventNotification s3EventNotification) {
        S3EventNotification.S3Entity s3Entity = s3EventNotification.getRecords().get(0).getS3();
        String objectKey = s3Entity.getObject().getKey();

        try (S3Object s3object = s3Client.getObject(bucketName, objectKey);
             InputStream inputStream = s3object.getObjectContent();
             InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
            bufferedReader.lines().forEach(line -> {
                String[] data = line.split(DELIMITER);
                dynamoDBService.createItem(new Inventory(data[UUID_INDEX], data[BOOK_UUID_INDEX], Integer.parseInt(data[QUANTITY_INDEX])));
            });
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
