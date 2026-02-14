package com.example.demo.service;

import com.example.demo.model.Product;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    private static final String KEY_PREFIX = "product:";

    public RedisService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void saveProduct(Product product) {
        String key = KEY_PREFIX + product.getId();
        redisTemplate.opsForValue().set(key, product, Duration.ofMinutes(10));
        System.out.println("Saved to Redis: " + key);
    }

    public Product getProduct(String id) {
        String key = KEY_PREFIX + id;
        return (Product) redisTemplate.opsForValue().get(key);
    }

    public void deleteProduct(String id) {
        String key = KEY_PREFIX + id;
        redisTemplate.delete(key);
        System.out.println("Deleted from Redis: " + key);
    }

}
