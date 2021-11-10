package com.example.microservices.product_inventory;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@Document(collection="product_inventory")
public class ProductInventory {
    @Id
    private String productId;
    private int quantity;

 


}
//
//{
//	  "id": "test-product-789",
//	  "title": "test-product-789",
//	  "desc": "test product 17879",
//	  "imagePath": "http://test-image-path2",
//	  "unitPrice": 20
//	}

//{
//	  "productId": "test-product-123",
//	  "quantity": 500,
//
//	}

//#db.product_inventory.insert({productId: "test_id", quantity:"test_quantity"})

//db.product_inventory.update({productId: "test_id"},{$set:{quantity:550}})
