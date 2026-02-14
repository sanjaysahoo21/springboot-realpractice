package com.example.rabbitmq_practice.consumer;

import org.springframework.stereotype.Service;

import com.example.rabbitmq_practice.dto.User;

import org.springframework.amqp.rabbit.annotation.RabbitListener;

@Service
public class RabbitMQConsumer {

    @RabbitListener(queues = "myQueue")
    public void consume(User user) {
        System.out.println("Consumed message: " + user.toString());
    }

}
