package com.sxh.rabbitmq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author SXH
 * @description 整合springboot 的rabbitmq 测试
 * @date 2021/1/24 12:12
 */
@SpringBootTest(classes = RabbitMQApplication.class)
@RunWith(SpringRunner.class)
public class AnnotationTest {
    // 注入rabbitmq 工具类
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 测试hello world 点对点的消息模型
     */
    @Test
    public void helloWorldSendTest() {
        rabbitTemplate.convertAndSend("hello", "hello world.");
    }

    /**
     * 测试工作队列消息模型
     */
    @Test
    public void workQueueSendTest() {
        // 模拟产生多条消息
        for (int i = 0; i < 10; i++) {
            rabbitTemplate.convertAndSend("work", "work queue test." + i);
        }
    }

    /**
     * 测试广播消息模型
     */
    @Test
    public void fanoutSendTest() {
        // 模拟产生多条消息
        for (int i = 0; i < 10; i++) {
            rabbitTemplate.convertAndSend("fanoutLogsExchange","",
                    "fanout exchange binding queue test." + i);
        }
    }

    /**
     * 测试direct消息模型 路由模型
     */
    @Test
    public void directSendTest() {
        // 模拟产生多条消息
        for (int i = 0; i < 10; i++) {
            rabbitTemplate.convertAndSend("directExchange","error",
                    "direct routing model test." + i);
        }
    }

    /**
     * 测试topic消息模型 动态路由模型  路由key  可以使用通配符
     */
    @Test
    public void topicSendTest() {
        // 模拟产生多条消息
        for (int i = 0; i < 10; i++) {
            // 消息：error.hh   error.hh.ss   debug.test.test.info  debug.test.info
            rabbitTemplate.convertAndSend("topicExchange","debug.test.info",
                    "topic routing model test." + i);
        }
    }
}
