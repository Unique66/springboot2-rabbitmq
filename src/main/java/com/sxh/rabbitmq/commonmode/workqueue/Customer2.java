package com.sxh.rabbitmq.commonmode.workqueue;

import com.rabbitmq.client.*;
import com.sxh.rabbitmq.commonmode.utils.RabbitMQConnectionUtils;

import java.io.IOException;
import java.time.LocalTime;
import java.util.concurrent.TimeoutException;

/**
 * @author SXH
 * @description 任务模型  一对多 多 <---> 消费者
 * @date 2021/1/18 22:52
 */
public class Customer2 {
    public static void main(String[] args) throws IOException, TimeoutException {
        // 1.使用工具类获取连接对象
        Connection connection = RabbitMQConnectionUtils.creatConnection();

        // 2.获取连接中的通道
        Channel channel = connection.createChannel();
        // ☆☆☆配置消费者每一次只能消费1 个消息
        channel.basicQos(1);
        // 3.通道绑定对应的消息队列
        // 参数1：队列名称，如果队列不存在，自动创建
        // 参数2：durable  用来定义队列特性是否要持久化  true-持久化  false-不持久化
        // 参数3：exclusive 是否独占队列 true-独占队列 false-不独占
        // 参数4：autoDelete 是否消费完后自动删除队列 true-自动删除  false-不自动删除
        // 参数5：arguments 额外附加参数
        channel.queueDeclare("work", true, false, true, null);

        // 4.消费消息
        // 参数1：队列名称  参数2：消息自动确认  true-消费者自动向rabbitmq 确认消息消费
        channel.basicConsume("work", false, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                try {
                    Thread.sleep(2000); // 模拟个别消费者消费消息过长的场景
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(LocalTime.now() + "消费者2----------->" + new String(body));
                // ☆☆☆参数1：确认队列中那个具体消息tag   参数2：是否开启多个消息同时确认
                channel.basicAck(envelope.getDeliveryTag(), false); // 也就是手动确认消息
            }
        });

        // 由于是消费者，所以需要一直监控队列的任务，不用关闭channel 和connection
//        channel.close();
//        connection.close();
    }
}
