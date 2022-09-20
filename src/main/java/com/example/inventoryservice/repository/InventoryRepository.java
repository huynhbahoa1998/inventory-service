package com.example.inventoryservice.repository;

import com.example.inventoryservice.model.Inventory;
import com.example.inventoryservice.model.InventoryId;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@EnableScan
public interface InventoryRepository extends CrudRepository<Inventory, InventoryId> {
}
