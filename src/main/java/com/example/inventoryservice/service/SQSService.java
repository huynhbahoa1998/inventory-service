package com.example.inventoryservice.service;

public interface SQSService {

    void sendMessage(String message);
}
