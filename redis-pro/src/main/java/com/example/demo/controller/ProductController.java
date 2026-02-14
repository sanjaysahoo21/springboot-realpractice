package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Product;
import com.example.demo.service.RedisService;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final RedisService redisService;

    public ProductController(RedisService redisService) {
        this.redisService = redisService;
    }

    @PostMapping
    public String saveProduct(@RequestBody Product product) {
        redisService.saveProduct(product);
        return "Product saved successfully";
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable String id) {
        return redisService.getProduct(id);
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable String id) {
        redisService.deleteProduct(id);
        return "Product deleted successfully";
    }

}
