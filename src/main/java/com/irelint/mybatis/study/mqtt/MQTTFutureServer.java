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
    private final static short KEEP_ALIVE = 30;// 低耗网络，但是又需要及时获取数据，心跳30s
    public  static Topic[] topics = {
            new Topic("/World", QoS.EXACTLY_ONCE)
    };
        /*    new Topic("china/tianjin", QoS.AT_LEAST_ONCE),
            new Topic("china/henan", QoS.AT_MOST_ONCE)};*/
    public final  static long RECONNECTION_ATTEMPT_MAX=6;
    public final  static long RECONNECTION_DELAY=2000;

    public final static int SEND_BUFFER_SIZE=2*1024*1024;//发送最大缓冲为2M
    public static void main(String[] args)   {
        MQTT mqtt = new MQTT();
        try {
            //设置服务端的ip
            mqtt.setHost(CONNECTION_STRING);
            //连接前清空会话信息
            mqtt.setCleanSession(CLEAN_START);
            //设置重新连接的次数
            mqtt.setReconnectAttemptsMax(RECONNECTION_ATTEMPT_MAX);
            //设置重连的间隔时间
            mqtt.setReconnectDelay(RECONNECTION_DELAY);
            //设置心跳时间
            mqtt.setKeepAlive(KEEP_ALIVE);
            //设置缓冲的大小
            mqtt.setSendBufferSize(SEND_BUFFER_SIZE);

            //创建连接
            final FutureConnection connection= mqtt.futureConnection();
            connection.connect();


            int count=1;
            Future messageFuture  = connection.receive();
            Message message = (Message) messageFuture.await();
            System.out.println("nihao" + message.getTopic());
           /* while(true){
                count++;
                // 用于发布消息，目前手机段不需要向服务端发送消息
                //主题的内容
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