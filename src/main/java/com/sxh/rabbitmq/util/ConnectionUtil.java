package com.sxh.rabbitmq.util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author SXH
 * @description 生成消息队列的
 * @date 2021/1/15 1:05
 */
public class ConnectionUtil {
    private static volatile ConnectionUtil connectionUtil;
    private ConnectionUtil() {}

    public static ConnectionUtil getInstance() {
        if (connectionUtil == null) {
            synchronized (ConnectionUtil.class) {
                if (connectionUtil == null) {
                    connectionUtil = new ConnectionUtil();
                }
            }
        }
        return connectionUtil;
    }

    public Connection creatConnection() throws IOException, TimeoutException {
        // 创建连接mq 的连接工厂对象
        ConnectionFactory connectionFactory = new ConnectionFactory();
        // 设置rabbitmq 的主机
        connectionFactory.setHost("169.254.171.100");
        // 设置端口号
        connectionFactory.setPort(5672);
        // 设置连接的虚拟主机
        connectionFactory.setVirtualHost("/test");
        // 设置访问账号和密码
        connectionFactory.setUsername("test");
        connectionFactory.setPassword("123456");

        Connection connection = connectionFactory.newConnection();
        return connection;
    }
}
