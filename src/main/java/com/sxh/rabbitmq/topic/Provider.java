package com.sxh.rabbitmq.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.sxh.rabbitmq.util.ConnectionUtil;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author SXH
 * @description Routing 订阅模式，Topic 动态路由， 生产者
 *              direct 的路由是写死的，topic 的路由可以根据通配符来匹配
 *              #匹配一个或多个单词，*匹配一个单词
 * @date 2021/1/19 23:08
 */
public class Provider {
    @Test
    public void provider() throws IOException, TimeoutException {
        // 1.获得rabbitMQ 的链接
        Connection connection = ConnectionUtil.creatConnection();
        // 2.创建通道
        Channel channel = connection.createChannel();
        // 3.声明交换机
        // 参数1：交换机名  参数2：交换机类型

        channel.exchangeDeclare("topicExchange", "topic");
        // 4.向交换机发送消息
        String routingKey = "warning.error.topic.debug";
        channel.basicPublish("topicExchange", routingKey, null,
                ("发送消息到topic routingKey：[" + routingKey + "]").getBytes());

        // 5.关闭资源
        ConnectionUtil.closeConnectionAndChanel(channel, connection);
    }
}
