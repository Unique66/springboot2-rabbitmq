package com.sxh.rabbitmq.integratedspringboot.fanout;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author SXH
 * @description 广播模式 消费者
 * @date 2021/1/24 12:25
 */
@Component
public class FanoutCustomer {
    @RabbitListener(bindings = {@QueueBinding(
            value = @Queue, // 创建临时队列
            exchange = @Exchange(value = "fanoutLogsExchange", type = "fanout") // 绑定交换机
    )})
    public void receive1(String message) {
        System.out.println("message1=" + message);
    }

    @RabbitListener(bindings = {@QueueBinding(
            value = @Queue, // 创建临时队列
            exchange = @Exchange(value = "fanoutLogsExchange", type = "fanout") // 绑定交换机
    )})
    public void receive2(String message) {
        System.out.println("message2=" + message);
    }
}
