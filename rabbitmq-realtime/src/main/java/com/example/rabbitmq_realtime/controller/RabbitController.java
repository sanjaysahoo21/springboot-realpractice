package com.example.rabbitmq_realtime.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.rabbitmq_realtime.publisher.MessagePublisher;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.rabbitmq_realtime.dto.User;

import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class RabbitController {

    @Autowired
    private MessagePublisher publisher;

    @PostMapping("/sendUser")
    public String sendUder(@RequestBody User user) {
        publisher.publish(user);
        return "sent: " + user;
    }

}
