package com.sxh.rabbitmq.commonmode.direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.sxh.rabbitmq.commonmode.utils.RabbitMQConnectionUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author SXH
 * @description Routing 订阅模式， direct 直连  生产者
 *              消息发送给指定routing 绑定的队列，消息不用发送给所有与exchange 交换机绑定的队列。
 * @date 2021/1/19 22:46
 */
public class Provider {
    @Test
    public void provider() throws IOException, TimeoutException {
        // 1.获得rabbitMQ 的链接
        Connection connection = RabbitMQConnectionUtils.creatConnection();
        // 2.创建通道
        Channel channel = connection.createChannel();
        // 3.声明交换机
        // 参数1：交换机名  参数2：交换机类型

        channel.exchangeDeclare("directExchange", "direct");
        // 4.向交换机发送消息
        String routingKey = "error";
        channel.basicPublish("directExchange", routingKey, null,
                ("发送消息到direct routingKey：[" + routingKey + "]").getBytes());

        // 5.关闭资源
        RabbitMQConnectionUtils.closeConnectionAndChanel(channel, connection);
    }
}
