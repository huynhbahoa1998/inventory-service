package com.example.inventoryservice.service;

import com.example.inventoryservice.model.Inventory;

public interface DynamoDBService {

    void createItem(Inventory inventory);
}
