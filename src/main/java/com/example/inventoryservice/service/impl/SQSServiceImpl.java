package com.example.inventoryservice.service.impl;

import com.example.inventoryservice.model.Inventory;
import com.example.inventoryservice.service.DynamoDBService;
import com.example.inventoryservice.service.S3Service;
import com.example.inventoryservice.service.SQSService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Service
public class SQSServiceImpl implements SQSService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SQSServiceImpl.class);

    @Value("${cloud.aws.sqs.queue.name}")
    private String queueName;

    @Autowired
    private QueueMessagingTemplate queueMessagingTemplate;

    @Autowired
    private S3Service s3Service;

    @Autowired
    private DynamoDBService dynamoDBService;

    @Override
    public void sendMessage(String message) {
        queueMessagingTemplate.send(queueName, MessageBuilder.withPayload(message).build());
    }

    @SqsListener("${cloud.aws.sqs.queue.name}")
    public void receiveMessage(String message) {
        try {
            InputStream inputStream = s3Service.getFileInputStream(message);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            bufferedReader.lines().forEach(line -> {
                String[] data = line.split(",");
                dynamoDBService.createItem(new Inventory(data[0], data[1], Integer.parseInt(data[2])));
            });
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }

    }
}
