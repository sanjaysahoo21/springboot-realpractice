package com.example.rabbitmq_realtime.publisher;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.rabbitmq_realtime.config.RabbitMQConfig;
import com.example.rabbitmq_realtime.dto.User;

@Service
public class MessagePublisher {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void publish(User user) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.MAIN_EXCHANGE,
                                        RabbitMQConfig.MAIN_ROUTING_KEY,
                                        user);

        System.out.println("Message sent : " + user);
    }

}
