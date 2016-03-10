package com.irelint.mybatis.study.mqtt;

import org.fusesource.mqtt.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URISyntaxException;

/**
 * author:zhaoht
 * date:2016/3/10 12:22
 */
public class MQTTFutureServer {
    private static final Logger LOG = LoggerFactory.getLogger(MQTTFutureServer.class);
    private final static String CONNECTION_STRING = "tcp://192.168.1.53:1883";
    private final static boolean CLEAN_START = true;
    private final static short KEEP_ALIVE = 30;// �ͺ����磬��������Ҫ��ʱ��ȡ���ݣ�����30s
    public  static Topic[] topics = {
            new Topic("/World", QoS.EXACTLY_ONCE)
    };
        /*    new Topic("china/tianjin", QoS.AT_LEAST_ONCE),
            new Topic("china/henan", QoS.AT_MOST_ONCE)};*/
    public final  static long RECONNECTION_ATTEMPT_MAX=6;
    public final  static long RECONNECTION_DELAY=2000;

    public final static int SEND_BUFFER_SIZE=2*1024*1024;//������󻺳�Ϊ2M
    public static void main(String[] args)   {
        MQTT mqtt = new MQTT();
        try {
            //���÷���˵�ip
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

            //��������
            final FutureConnection connection= mqtt.futureConnection();
            connection.connect();


            int count=1;
            Future messageFuture  = connection.receive();
            Message message = (Message) messageFuture.await();
            System.out.println("nihao" + message.getTopic());
           /* while(true){
                count++;
                // ���ڷ�����Ϣ��Ŀǰ�ֻ��β���Ҫ�����˷�����Ϣ
                //���������
                String message="hello "+count+"chinese people !";
                String topic = "china/beijing";
                connection.publish(topic, message.getBytes(), QoS.AT_LEAST_ONCE,
                        false);
                System.out.println("MQTTFutureServer.publish Message "+"Topic Title :"+topic+" context :"+message);

            }*/
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}