package com.sxh.rabbitmq.fanout;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.sxh.rabbitmq.util.ConnectionUtil;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author SXH
 * @description 发布订阅模式 fanout 生产者，至于exchange 交换器通信，
 *              交换器绑定不同的queue，完成一个消息多个消费者同时消费
 * @date 2021/1/18 23:54
 */
public class Provider {
    // 生产消息
    @Test
    public void testSendMessage() throws IOException, TimeoutException {
        // 1.使用工具类获取连接对象
        Connection connection = ConnectionUtil.creatConnection();

        // 2.获取连接中的通道
        Channel channel = connection.createChannel();

        // 3.将通道与交换机broadcast 绑定，定义交换机的类型为fanout
        // 参数1：交换机名称  参数2：交换机类型 fanout 为广播类型
        channel.exchangeDeclare("broadcast", "fanout");

        // 4.发布消息
        // 参数1：exchange 交换机名称
        // 参数2：routingKey 路由key
        // 参数3：传递消息额外配置
        // 参数4：消息的具体内容
        channel.basicPublish("broadcast", "", null, "fanout message rabbitmq".getBytes());
        // 5.关闭连接
        ConnectionUtil.closeConnectionAndChanel(channel, connection);
    }
}
