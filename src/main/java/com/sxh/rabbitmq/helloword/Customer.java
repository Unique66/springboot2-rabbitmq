package com.sxh.rabbitmq.helloword;

import com.rabbitmq.client.*;
import com.sxh.rabbitmq.util.ConnectionUtil;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalTime;
import java.util.concurrent.TimeoutException;

/**
 * @author SXH
 * @description 消费者
 * @date 2021/1/15 1:02
 */
public class Customer {
    // 消费消息
    public static void main(String[] args) throws IOException, TimeoutException {
        // 1.使用工具类(Singleton)获取连接对象
        Connection connection = ConnectionUtil.creatConnection();

        // 2.获取连接中的通道
        Channel channel = connection.createChannel();

        // 3.通道绑定对应的消息队列
        // 参数1：队列名称，如果队列不存在，自动创建
        // 参数2：durable  用来定义队列特性是否要持久化  true-持久化  false-不持久化
        // 参数3：exclusive 是否独占队列 true-独占队列 false-不独占
        // 参数4：autoDelete 是否消费完后自动删除队列 true-自动删除  false-不自动删除
        // 参数5：arguments 额外附加参数
        channel.queueDeclare("hello", true, false, true, null);

        // 4.消费消息
        channel.basicConsume("hello", true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
//                super.handleDelivery(consumerTag, envelope, properties, body);
                System.out.println(LocalTime.now() + "----------->" + new String(body));
            }
        });

        // 由于是消费者，所以需要一直监控队列的任务，不用关闭channel 和connection
//        channel.close();
//        connection.close();
    }
}
