package com.example.microservices.product_catalog;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="product_inventory")
public class ProductInventory {
    @Id
    private String productId;
    private int quantity;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }



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