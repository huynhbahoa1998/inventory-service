package com.example.inventoryservice.service.impl;

import com.example.inventoryservice.model.Inventory;
import com.example.inventoryservice.repository.InventoryRepository;
import com.example.inventoryservice.service.DynamoDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DynamoDBServiceImpl implements DynamoDBService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Override
    public void createItem(Inventory inventory) {
        inventoryRepository.save(inventory);
    }
}
