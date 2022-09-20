package com.example.inventoryservice.model;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@NoArgsConstructor
@DynamoDBTable(tableName = "Inventory")
public class Inventory {

    @Id
    @DynamoDBIgnore
    private InventoryId id;

    @DynamoDBHashKey(attributeName = "Uuid")
    private String uuid;

    @DynamoDBRangeKey(attributeName = "BookUuid")
    private String bookUuid;

    @DynamoDBAttribute(attributeName = "Quantity")
    private int quantity;

    public Inventory(String uuid, String bookUuid, int quantity) {
        this.uuid = uuid;
        this.bookUuid = bookUuid;
        this.quantity = quantity;
    }
}
