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
        mqtt.setReconnectBackOffMultiplier(2);//����������ָ���ع顣����Ϊ1��ͣ��ָ���ع飬Ĭ��Ϊ2

        //Socket����˵��
        mqtt.setReceiveBufferSize(65536);//����socket���ջ�������С��Ĭ��Ϊ65536��64k��
        mqtt.setSendBufferSize(65536);//����socket���ͻ�������С��Ĭ��Ϊ65536��64k��
        mqtt.setTrafficClass(8);//���÷������ݰ�ͷ���������ͻ���������ֶΣ�Ĭ��Ϊ8����Ϊ��������󻯴���

        //������������˵��
        mqtt.setMaxReadRate(0);//�������ӵ����������ʣ���λΪbytes/s��Ĭ��Ϊ0����������
        mqtt.setMaxWriteRate(0);//�������ӵ���������ʣ���λΪbytes/s��Ĭ��Ϊ0����������

        //ѡ����Ϣ�ַ�����
        mqtt.setDispatchQueue(Dispatch.createQueue("foo"));//��û�е��÷���setDispatchQueue���ͻ��˽�Ϊ�����½�һ�����С������ʵ�ֶ������ʹ�ù��õĶ��У���ʽ��ָ��������һ���ǳ������ʵ�ַ���


        BlockingConnection connection = mqtt.blockingConnection();
        connection.connect();;
        connection.publish("hello world!","Hello".getBytes(), QoS.AT_LEAST_ONCE,false);
        //������subscribe�������Ķ������
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
