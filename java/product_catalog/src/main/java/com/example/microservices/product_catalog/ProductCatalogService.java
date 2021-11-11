package com.example.microservices.product_catalog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ProductCatalogService {
//    
//    @Autowired
//    private MongoTemplate mongoTemplate;
//
//    @PostMapping("/product")
//    public Product addProduct(@RequestBody Product product){
//        return mongoTemplate.insert(product);
//    }
//
//    @PutMapping("/product")
//    public Product updateProduct(@RequestBody Product product){
//        return mongoTemplate.save(product);        
//    }
//
////    @GetMapping("/product/{id}")
////    public Product getProductDetails(@PathVariable  String id){
////        return mongoTemplate.findById(id,Product.class);
////    }
//
//    
//    @GetMapping("/product/{id}")
//    public Product getProductDetails(@PathVariable String id) {
//        Product product = mongoTemplate.findById(id, Product.class);
//        ProductInventory productInventory = restTemplate.getForObject("http://product-inventory:8080/inventory/" + id, ProductInventory.class);
//		product.setProductInventory(productInventory);
//		return product;
//    }
//    
//
// 
//    
//    @DeleteMapping("/product/{id}")
//    public String deleteProduct(@PathVariable String id) {
//        Product toDeleteProduct = new Product();
//        toDeleteProduct.setId(id);
//
//        mongoTemplate.remove(toDeleteProduct);
//        return "Product Deleted-"+id;
//    }
//
//    @GetMapping("/product")
//    public List<Product> getProductList(){
//        return mongoTemplate.findAll(Product.class);
//    }
//    
    
    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private RestTemplate restTemplate;
    
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder){
     
        
        
        RestTemplate  restTemplate = builder.build();

//        List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();        
//
//        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
//        converter.setSupportedMediaTypes( Arrays.asList(
//                MediaType.APPLICATION_JSON, 
//                MediaType.APPLICATION_OCTET_STREAM));         
//        messageConverters.add(converter);  
//        restTemplate.setMessageConverters(messageConverters);    
        
        return restTemplate;
        

    }

    @PostMapping("/product")
    public Product addProduct(@RequestBody Product product){
        return mongoTemplate.insert(product);
    }

    @PutMapping("/product")
    public Product updateProduct(@RequestBody Product product){
        return mongoTemplate.save(product);        
    }

    @Value("${PRODUCT_INVENTORY_DEPLOYMENT_SERVICE_HOST}")
    private String psHost;

    @Value("${PRODUCT_INVENTORY_DEPLOYMENT_SERVICE_PORT}")
    private String psPort;
//    
    @Value("${MONGODB_PC_URI}")
    private String url;
    
    
    @GetMapping("/pi_host")
    public String getPiHost(){
        return psHost;
    }
    
    @GetMapping("/pi_port")
    public String getPiPort(){
        return psPort;
    }
    
    @GetMapping("/product/url")
    public String getUrl(){
        return url;
    }


//    @GetMapping("/product/{id}")
//    public Product getProductDetails(@PathVariable  String id){
//        Product product =  mongoTemplate.findById(id,Product.class);
//
//        return product;
//    }
    
    @GetMapping("/product/{id}")
    public Product getProductDetails(@PathVariable  String id){
        Product product =  mongoTemplate.findById(id,Product.class);
        ProductInventory productInventory = restTemplate.getForObject("http://"+psHost+":"+psPort+"/inventory/" + id,ProductInventory.class);
//        ProductInventory productInventory = restTemplate.getForObject("http://product-inventory-service:8080/inventory/" + id,ProductInventory.class);

        product.setProductInventory(productInventory);

        return product;
    }

    
    @GetMapping("/product/inventory")
    public List<ProductInventory> getInventoryList(){
   
    	ProductInventory[] piArray = restTemplate.getForObject("http://"+psHost+":"+psPort+"/inventory/arr", ProductInventory[].class);
        return Arrays.asList(piArray);
    }
    
    @PostMapping("/product/inventory")
    public ProductInventory addInventory(@RequestBody ProductInventory inv){
   
        return restTemplate.postForObject("http://"+psHost+":"+psPort+"/inventory/" ,inv, ProductInventory.class);

    }


    @DeleteMapping("/product/{id}")
    public String deleteProduct(@PathVariable String id) {
        Product toDeleteProduct = new Product();
        toDeleteProduct.setId(id);

        mongoTemplate.remove(toDeleteProduct);
        return "Product Deleted-"+id;
    }

//    @GetMapping("/product")
//    public List<Product> getProductList(){
//        return mongoTemplate.findAll(Product.class)
//    }
  
   @GetMapping("/product")
    public List<Product> getProductList(){
    	List<Product> list_of_prd = new ArrayList<>();
        for( Product p: mongoTemplate.findAll(Product.class)){
        	Product prd = getProductDetails(p.getId());
        	list_of_prd.add(prd);
        }
		return list_of_prd;
    }
    
//    @GetMapping("/product")
//    public List<Product> getProductList(){
//    	List<Product> list_of_prd = new ArrayList<>();
//        for( Product p: mongoTemplate.findAll(Product.class)){
//        	list_of_prd.add(p);
//        }
//		return list_of_prd;
//    }

}