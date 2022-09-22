package com.example.inventoryservice.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.example.inventoryservice.model.Inventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class InventoryRepository {

    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    public void createInventory(Inventory inventory) {
        dynamoDBMapper.save(inventory);
    }
}
