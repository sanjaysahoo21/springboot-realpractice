package com.example.rabbitmq_practice.publisher;

import org.springframework.stereotype.Service;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.rabbitmq_practice.dto.User;

@Service
public class RabbitMQProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendJsonMessage(User user) {
        rabbitTemplate.convertAndSend("myQueue", user);
        System.out.println("Message sent to RabbitMQ: " + user);
    }

}
