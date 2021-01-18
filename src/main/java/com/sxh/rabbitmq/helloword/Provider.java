package com.sxh.rabbitmq.helloword;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import com.sxh.rabbitmq.util.ConnectionUtil;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author SXH
 * @description 测试点对点的消息生产消息消费模型
 * @date 2021/1/15 0:30
 */
public class Provider {

    // 生产消息
    @Test
    public void testSendMessage() throws IOException, TimeoutException {
//        // 创建连接mq 的连接工厂对象
//        ConnectionFactory connectionFactory = new ConnectionFactory();
//        // 设置rabbitmq 的主机
//        connectionFactory.setHost("169.254.171.100");
//        // 设置端口号
//        connectionFactory.setPort(5672);
//        // 设置连接的虚拟主机
//        connectionFactory.setVirtualHost("/test");
//        // 设置访问账号和密码
//        connectionFactory.setUsername("test");
//        connectionFactory.setPassword("123456");
//
//        // 1.获取连接对象
//        Connection connection = connectionFactory.newConnection();

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

        // 4.发布消息
        // 参数1：exchange 交换机名称
        // 参数2：routingKey 队列名称
        // 参数3：传递消息额外配置
        // 参数4：消息的具体内容
        channel.basicPublish("", "hello", MessageProperties.MINIMAL_PERSISTENT_BASIC,
                "hello rabbitmq".getBytes());

        ConnectionUtil.closeConnectionAndChanel(channel, connection);
    }
}
