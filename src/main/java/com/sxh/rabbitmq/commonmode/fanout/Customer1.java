package com.sxh.rabbitmq.commonmode.fanout;

import com.rabbitmq.client.*;
import com.sxh.rabbitmq.commonmode.utils.RabbitMQConnectionUtils;

import java.io.IOException;
import java.time.LocalTime;
import java.util.concurrent.TimeoutException;

/**
 * @author SXH
 * @description 发布订阅模式 消费者1
 * @date 2021/1/19 0:04
 */
public class Customer1 {
    public static void main(String[] args) throws IOException, TimeoutException {
        // 1.使用工具类获取连接对象
        Connection connection = RabbitMQConnectionUtils.creatConnection();

        // 2.获取连接中的通道
        Channel channel = connection.createChannel();

        // 3.通道绑定交换机
        channel.exchangeDeclare("broadcast", "fanout");
        // 4.声明一个临时队列
        String queueName = channel.queueDeclare().getQueue();
        // 5.将交换机与队列绑定
        // 参数1：队列名 参数2：交换机名 参数3：路由key
        channel.queueBind(queueName, "broadcast", "");

        // 6.消费消息
        // 参数1：队列名称  参数2：消息自动确认  true-消费者自动向rabbitmq 确认消息消费
        channel.basicConsume(queueName, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println(LocalTime.now() + "消费者1----------->" + new String(body));
            }
        });

        // 由于是消费者，所以需要一直监控队列的任务，不用关闭channel 和connection
//        channel.close();
//        connection.close();
    }
}
