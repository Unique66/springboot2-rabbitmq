package com.sxh.rabbitmq.integratedspringboot.direct;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author SXH
 * @description 路由模型 消费者
 * @date 2021/1/24 12:38
 */
@Component
public class DirectCustomer {
    @RabbitListener(bindings = {@QueueBinding(
            value = @Queue, // 临时队列
            exchange = @Exchange(value = "directExchange", type = "direct"), // 指定交换机的名称和类型，默认交换机类型就是 direct
            key = {"info", "error", "warning"}

    )})
    public void receive1(String message) {
        System.out.println("message1=" + message);
    }

    @RabbitListener(bindings = {@QueueBinding(
            value = @Queue, // 临时队列
            exchange = @Exchange(value = "directExchange", type = "direct"), // 指定交换机的名称和类型，默认交换机类型就是 direct
            key = {"info"}

    )})
    public void receive2(String message) {
        System.out.println("message2=" + message);
    }
}
