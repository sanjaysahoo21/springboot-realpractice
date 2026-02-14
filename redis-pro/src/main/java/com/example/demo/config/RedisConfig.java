package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        // key serializer : use simple strings for keys (eg : "product: 1")
        template.setKeySerializer(new StringRedisSerializer());
        // value serializer : use json for values (eg : "{name: 'product1', price:
        // 100}")
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return template;
    }

}
