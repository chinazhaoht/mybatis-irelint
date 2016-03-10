package com.irelint.mybatis.study.mqtt;

import org.fusesource.hawtdispatch.Dispatch;
import org.fusesource.mqtt.client.*;

import java.net.URISyntaxException;

/**
 * author:zhaoht
 * date:2016/3/10 9:48
 */

public class MQTTTest {

    public static void main(String[] args) throws Exception {



        MQTT mqtt = new MQTT();
        //setting

        mqtt.setHost("tcp://192.168.1.53:1883");
        mqtt.setClientId("0123456789");
        mqtt.setCleanSession(false);
        mqtt.setKeepAlive((short)60);
        mqtt.setUserName("zhaoht");
        mqtt.setPassword("zhaoht");
        mqtt.setWillTopic("willTopic");
        mqtt.setWillMessage("willMessage");
        mqtt.setWillQos(QoS.AT_LEAST_ONCE);
        mqtt.setWillRetain(true);
        mqtt.setWillRetain(true);

        mqtt.setConnectAttemptsMax(10L);
        mqtt.setReconnectAttemptsMax(3L);
        mqtt.setReconnectDelay(10L);
        mqtt.setReconnectDelayMax(30000L);
        mqtt.setReconnectBackOffMultiplier(2);//设置重连接指数回归。设置为1则停用指数回归，默认为2

        //Socket设置说明
        mqtt.setReceiveBufferSize(65536);//设置socket接收缓冲区大小，默认为65536（64k）
        mqtt.setSendBufferSize(65536);//设置socket发送缓冲区大小，默认为65536（64k）
        mqtt.setTrafficClass(8);//设置发送数据包头的流量类型或服务类型字段，默认为8，意为吞吐量最大化传输

        //带宽限制设置说明
        mqtt.setMaxReadRate(0);//设置连接的最大接收速率，单位为bytes/s。默认为0，即无限制
        mqtt.setMaxWriteRate(0);//设置连接的最大发送速率，单位为bytes/s。默认为0，即无限制

        //选择消息分发队列
        mqtt.setDispatchQueue(Dispatch.createQueue("foo"));//若没有调用方法setDispatchQueue，客户端将为连接新建一个队列。如果想实现多个连接使用公用的队列，显式地指定队列是一个非常方便的实现方法


        BlockingConnection connection = mqtt.blockingConnection();
        connection.connect();;
        connection.publish("hello world!","Hello".getBytes(), QoS.AT_LEAST_ONCE,false);
        //可以用subscribe方法订阅多个主题
        Topic[] topics = {new Topic("foo", QoS.AT_LEAST_ONCE)};
        byte[] qoses = connection.subscribe(topics);
        Message message = connection.receive();
        System.out.println(message.getTopic());
        byte[] payload = message.getPayload();
        //process the message then;
        message.ack();
        connection.disconnect();

    }

}
