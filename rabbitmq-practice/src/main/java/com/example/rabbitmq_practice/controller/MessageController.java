package com.example.rabbitmq_practice.controller;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.rabbitmq_practice.publisher.RabbitMQProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.example.rabbitmq_practice.dto.User;

@RestController
public class MessageController {

    @Autowired
    private RabbitMQProducer producer;

    @PostMapping("/send")
    public String sendJsonMessage(@RequestBody User user) {
        producer.sendJsonMessage(user);
        return "Message sent to RabbitMQ: " + user;
    }

}
