package com.example.inventoryservice.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventoryId {

    @DynamoDBHashKey
    private String uuid;

    @DynamoDBRangeKey
    private String bookUuid;
}
