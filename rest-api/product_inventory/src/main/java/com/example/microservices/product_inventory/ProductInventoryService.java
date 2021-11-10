package com.example.microservices.product_inventory;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class ProductInventoryService {
    
    @Autowired
    private MongoTemplate mongoTemplate;


    @PostMapping("/inventory")
    public ProductInventory addProductInventory(@RequestBody ProductInventory product){
        return mongoTemplate.insert(product);
    }

    @PutMapping("/inventory")
    public ProductInventory updateProductInventory(@RequestBody ProductInventory product){
        return mongoTemplate.save(product);        
    }

    @GetMapping("/inventory/{id}")
    public ProductInventory getProductInventory(@PathVariable  String id){
        return mongoTemplate.findById(id,ProductInventory.class);
    }
    
    @GetMapping("/inventory")
    public List<ProductInventory> getProductList(){
        return mongoTemplate.findAll(ProductInventory.class);
    }

//    @Value("${MONGO_PASSWORD}")
//    private String pwd;
//
//    @Value("${MONGO_USERNAME}")
//    private String username;
//    
    @Value("${MONGODB_PI_URI}")
    private String url;
    
//    @Value("${PI_MONGO_HOST}")
//    private String host;
//    
//    @Value("${MONGO_PORT}")
//    private String port;
    

    
//    @GetMapping("/username")
//    public String getUsername(){
//        return username;
//    }
//    
//    @GetMapping("/pwd")
//    public String getPwd(){
//        return pwd;
//    }
//    
    @GetMapping("/inventory/url")
    public String getUrl(){
        return url;
    }
    
//    @GetMapping("/pihost")
//    public String getHost(){
//        return host;
//    }
//    
//    @GetMapping("/port")
//    public String getPort(){
//        return port;
//    }
}