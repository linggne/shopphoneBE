package com.shopPhone.shopphoneBE.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shopPhone.shopphoneBE.model.MessageResponse;
import com.shopPhone.shopphoneBE.model.ProductRequest;
import com.shopPhone.shopphoneBE.model.ProductResponse;
import com.shopPhone.shopphoneBE.service.IProductService;

@CrossOrigin
@RestController
@RequestMapping("/api/products")
// @PreAuthorize("hasAuthority('Admin')")
public class ProductController {

    private final IProductService productService;

    @Autowired
    public ProductController(IProductService productService){
        this.productService = productService;
    }
    
    @GetMapping("")
    public ResponseEntity<?> getProduct(@RequestParam(required = false) String brandId, @RequestParam(required = false) String status, @RequestParam(required = false) String type){
        Map<String, Object> filters = new HashMap<>();
        filters.put("brandId", brandId);
        filters.put("type", type);
        filters.put("status", status);
        return ResponseEntity.ok().body(productService.findAllProduct(filters));
    }
    @PostMapping("")
    public ResponseEntity<?> createProduct(@RequestBody ProductRequest productRequest){
        ProductResponse product  = productService.createProduct(productRequest);
        return ResponseEntity.ok().body(product);
    }
    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable int productId){
        productService.deleteProduct(productId);
        return ResponseEntity.ok().body(new MessageResponse("deleted successful!"));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable int productId, @RequestBody ProductRequest productRequest){
        Boolean res = productService.updateProduct(productRequest, productId);
        if(res){
            return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("update successful!"));
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("update fail!"));
        }
    }

    @GetMapping("/{productId}")
    public ResponseEntity<?> getProductById(@PathVariable int productId){
        return ResponseEntity.ok().body(productService.getProductByID(productId));
    }
}
