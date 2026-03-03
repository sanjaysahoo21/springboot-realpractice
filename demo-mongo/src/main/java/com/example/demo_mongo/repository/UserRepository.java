package com.example.demo_mongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo_mongo.model.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    
}
