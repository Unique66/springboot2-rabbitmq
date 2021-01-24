package com.sxh.rabbitmq.integratedspringboot.topic;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author SXH
 * @description 动态路由模式 消费者
 * @date 2021/1/24 12:57
 */
@Component
public class TopicCustomer {
    @RabbitListener(bindings = {@QueueBinding(
            value = @Queue, // 生成临时的队列
            exchange = @Exchange(value = "topicExchange", type = "topic"), // 指定交换机的名称和类型
            key = {"error.*", "debug.#.info"} // 路由key  可以使用通配符
    )})
    public void receive1(String message) {
        System.out.println("message1=" + message);
    }

    @RabbitListener(bindings = {@QueueBinding(
            value = @Queue, // 生成临时的队列
            exchange = @Exchange(value = "topicExchange", type = "topic"), // 指定交换机的名称和类型
            key = {"error.#", "debug.*.info"} // 路由key  可以使用通配符
    )})
    public void receive2(String message) {
        System.out.println("message2=" + message);
    }
}
