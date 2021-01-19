package com.sxh.rabbitmq.integratedspringboot.helloworld;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author SXH
 * @description 点对点模型 消费者  注解方式实现
 * @date 2021/1/20 0:29
 */
@Component
@RabbitListener(queuesToDeclare = @Queue(value = "hello",
        durable = "true", autoDelete = "true"))
public class HelloConsumer {
    @RabbitHandler
    public void receive(String message) {
        System.out.println("message=" + message);
    }
}
