package com.example.microservices.product_catalog;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Document(collection="product")
@Setter @Getter
public class Product {
    @Id
    private String id;
    private String title;
    private String desc;
    private String imagePath;
    private double unitPrice;
    private ProductInventory productInventory;

    

    @Override
    public String toString() {
        return "Product [desc=" + desc + ", id=" + id + ", imagePath=" + imagePath + ", title=" + title + ", unitPrice="
                + unitPrice + "]";
    }
}


