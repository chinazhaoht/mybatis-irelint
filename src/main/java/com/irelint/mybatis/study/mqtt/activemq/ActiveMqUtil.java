package com.irelint.mybatis.study.mqtt.activemq;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * author:zhaoht
 * date:2016/3/9 10:28
 */

public class ActiveMqUtil {

    public static void main(String[] args){

        //链接工厂
        ConnectionFactory connectionFactory;
        //创建链接
        Connection connection = null;
        //创建一个session
        Session session;
        //创建目的地
        Destination destination;

        //消息提供者
        MessageProducer messageProducer;
        //构造ConnectionFactory
        connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER,ActiveMQConnection.DEFAULT_PASSWORD,"tcp://localhost:616161");
        //设置用户名和密码
        //coonnectionFactory.setUsername("username")
        //connectionFactory.setPassword("password")
        try{
            //得到链接对象
            connection = connectionFactory.createConnection();
            //启动链接
            connection.start();

            //创建Session,
            session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
            //获取服务商信息
            destination = session.createQueue("你的active里面创建的queue的名称");
            //得到消息发送者
            messageProducer = session.createProducer(destination);
            messageProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);


            String message = "hello world!";
            sendMessage(session,messageProducer,message);
            session.commit();
        } catch (JMSException e) {
            e.printStackTrace();
        }finally {
            try{
                if(connection != null){
                    connection.close();
                }
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }

    public static void sendMessage(Session session, MessageProducer messageProducer, String messages) throws JMSException {
        TextMessage message = session.createTextMessage(messages);
        messageProducer.send(message);
    }
}
