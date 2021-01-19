package com.sxh.rabbitmq.integratedspringboot.helloworld;

import com.sxh.rabbitmq.RabbitMQApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author SXH
 * @description 点对点模式
 * @date 2021/1/19 23:58
 */
@SpringBootTest(classes = RabbitMQApplication.class)
@RunWith(SpringRunner.class)
public class HelloProvider {
    // 注入rabbitmq
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void helloWorldSend() {
        rabbitTemplate.convertAndSend("hello", "hello world");
    }
}
