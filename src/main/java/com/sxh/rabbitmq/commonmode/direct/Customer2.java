package com.sxh.rabbitmq.commonmode.direct;

import com.rabbitmq.client.*;
import com.sxh.rabbitmq.commonmode.utils.RabbitMQConnectionUtils;

import java.io.IOException;
import java.time.LocalTime;
import java.util.concurrent.TimeoutException;

/**
 * @author SXH
 * @description Routing 订阅模式， direct 直连  消费者
 * @date 2021/1/19 22:56
 */
public class Customer2 {
    public static void main(String[] args) throws IOException, TimeoutException {
        // 1.获得rabbitmq 链接
        Connection connection = RabbitMQConnectionUtils.creatConnection();
        // 2.获得channel
        Channel channel = connection.createChannel();
        // 3.定义exchange 交换机
        channel.exchangeDeclare("directExchange", "direct");
        // 4.获得随机队列，并与交换机绑定
        String queueName = channel.queueDeclare().getQueue();
        // 参数3：路由名   绑定多个routing
        channel.queueBind(queueName, "directExchange", "error");
        channel.queueBind(queueName, "directExchange", "debug");
        channel.queueBind(queueName, "directExchange", "warning");
        // 5.监控交换机绑定的队列数据，并消费
        channel.basicConsume(queueName, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println(LocalTime.now() + "direct消费者2----------->" + new String(body));
            }
        });
    }
}
