package com.example.rabbitmq_realtime.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.example.rabbitmq_realtime.config.RabbitMQConfig;
import com.example.rabbitmq_realtime.dto.User;

@Service
public class MessageConsumer {

    @RabbitListener(queues = RabbitMQConfig.MAIN_QUEUE)
    public void recieve(User user) {
        System.out.println("Message Recieved : " + user);

        if ("fail".equalsIgnoreCase(user.getName())) {
            System.out.println("ALERT : Message is failed. Moving to DLQ.....");
            throw new RuntimeException("Message Processing Failed");
        }
    }

    @RabbitListener(queues = RabbitMQConfig.DLQ_QUEUE)
    public void processFailedMessages(User failedUser) {
        System.out.println("Recived in dead letter queue:" + failedUser);
    }

}
