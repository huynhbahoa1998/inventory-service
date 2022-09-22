package com.example.inventoryservice.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBTable(tableName = "Inventory")
public class Inventory {

    @DynamoDBHashKey(attributeName = "Uuid")
    private String uuid;

    @DynamoDBRangeKey(attributeName = "BookUuid")
    private String bookUuid;

    @DynamoDBAttribute(attributeName = "Quantity")
    private int quantity;
}
