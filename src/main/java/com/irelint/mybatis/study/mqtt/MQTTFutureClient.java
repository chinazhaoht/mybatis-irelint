package com.irelint.mybatis.study.mqtt;

import org.fusesource.mqtt.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URISyntaxException;

/**
 * author:zhaoht
 * date:2016/3/10 12:23
 */
public class MQTTFutureClient {
    private static final Logger LOG = LoggerFactory.getLogger(MQTTFutureClient.class);
    private final static String CONNECTION_STRING = "tcp://192.168.1.53:1883";
    private final static boolean CLEAN_START = true;
    private final static short KEEP_ALIVE = 30;// �ͺ����磬��������Ҫ��ʱ��ȡ���ݣ�����30s
    private final static String CLIENT_ID = "publishService";
    public  static Topic[] topics = {
            new Topic("china/beijing", QoS.EXACTLY_ONCE),
            new Topic("china/tianjin", QoS.AT_LEAST_ONCE),
            new Topic("china/henan", QoS.AT_MOST_ONCE)};
    public final  static long RECONNECTION_ATTEMPT_MAX=6;
    public final  static long RECONNECTION_DELAY=2000;

    public final static int SEND_BUFFER_SIZE=2*1024*1024;//������󻺳�Ϊ2M


    public static void main(String[] args)   {
        //����MQTT����
        MQTT mqtt = new MQTT();
        try {
            //����mqtt broker��ip�Ͷ˿�
            mqtt.setHost(CONNECTION_STRING);
            //����ǰ��ջỰ��Ϣ
            mqtt.setCleanSession(CLEAN_START);
            //�����������ӵĴ���
            mqtt.setReconnectAttemptsMax(RECONNECTION_ATTEMPT_MAX);
            //���������ļ��ʱ��
            mqtt.setReconnectDelay(RECONNECTION_DELAY);
            //��������ʱ��
            mqtt.setKeepAlive(KEEP_ALIVE);
            //���û���Ĵ�С
            mqtt.setSendBufferSize(SEND_BUFFER_SIZE);

            //��ȡmqtt�����Ӷ���BlockingConnection
            final FutureConnection connection= mqtt.futureConnection();
            connection.connect();
            connection.subscribe(topics);
            while(true){
                Future<Message> futrueMessage=connection.receive();
                Message message =futrueMessage.await();


                System.out.println("MQTTFutureClient.Receive Message "+ "Topic Title :"+message.getTopic()+" context :"+String.valueOf(message.getPayloadBuffer()));
            }
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{

        }
    }
}
