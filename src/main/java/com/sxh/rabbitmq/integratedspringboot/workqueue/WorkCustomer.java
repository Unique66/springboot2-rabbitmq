package com.sxh.rabbitmq.integratedspringboot.workqueue;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListeners;
import org.springframework.stereotype.Component;

/**
 * @author SXH
 * @description 工作队列模型  消费者
 * @date 2021/1/24 12:20
 */
@Component
public class WorkCustomer {

    @RabbitListener(queuesToDeclare = @Queue(value = "work"))
    public void receive1(String message) {
        System.out.println("message1=" + message);
    }

    @RabbitListener(queuesToDeclare = @Queue(value = "work"))
    public void receive2(String message) {
        System.out.println("message2=" + message);
    }
}
