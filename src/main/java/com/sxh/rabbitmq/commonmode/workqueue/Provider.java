package com.sxh.rabbitmq.commonmode.workqueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.sxh.rabbitmq.commonmode.utils.RabbitMQConnectionUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author SXH
 * @description 任务模型  一对多   一 <---> 生产者
 * @date 2021/1/18 22:49
 */
public class Provider {
    // 生产消息
    @Test
    public void testSendMessage() throws IOException, TimeoutException {
        // 1.使用工具类获取连接对象
        Connection connection = RabbitMQConnectionUtils.creatConnection();

        // 2.获取连接中的通道
        Channel channel = connection.createChannel();

        // 3.通道绑定对应的消息队列
        // 参数1：队列名称，如果队列不存在，自动创建
        // 参数2：durable  用来定义队列特性是否要持久化  true-持久化  false-不持久化
        // 参数3：exclusive 是否独占队列 true-独占队列 false-不独占
        // 参数4：autoDelete 是否消费完后自动删除队列 true-自动删除  false-不自动删除
        // 参数5：arguments 额外附加参数
        channel.queueDeclare("work", true, false, true, null);

        // 4.发布消息
        for (int i = 0; i < 20; i++) {
            // 参数1：exchange 交换机名称
            // 参数2：routingKey 队列名称
            // 参数3：传递消息额外配置
            // 参数4：消息的具体内容
            channel.basicPublish("", "work", null, (i + "work rabbitmq").getBytes());
        }
        // 5.关闭连接
        RabbitMQConnectionUtils.closeConnectionAndChanel(channel, connection);
    }
}
