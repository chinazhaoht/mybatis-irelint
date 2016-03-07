package com.irelint.mybatis.service;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.data.Stat;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.Serializers;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * author:zhaoht
 * date:2016/3/4
 */

public class Zookeeper {

    static final String connectingString = "120.27.127.2:2181,120.27.127.2:3181";


    public static void main(String[] args) throws Exception {
       RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000,3);
        CuratorFramework client = CuratorFrameworkFactory.newClient(connectingString,
                5000,
                3000,
                retryPolicy);
        client.start();

       // client.create().forPath("/hello","init".getBytes());

        //client.delete().forPath("/hello");

        Stat stat = new Stat();
        System.out.println(new String(client.getData().storingStatIn(stat).forPath("/hello")));
        client.getData().forPath("/hello");



    }
    public static void hello() throws IOException {

        Student student =  new Student("zhaoht",23);
        Date beginTime = new Date();
        ObjectMapper mapper = new ObjectMapper();
      //  mapper.setVisibility(JsonMethod.FIELD, JsonAutoDetect.Visibility.ANY);
        List<Student> students = new ArrayList<Student>();

        Map<String,Integer> map = new ConcurrentHashMap<String, Integer>();
        map.put(student.getName(),student.getAge());



        String s = null;
        int i = 100;
        while( i > 0){
            s  = mapper.writeValueAsString(student);
            students.add(student);
            i--;
        }
        long midTime = System.currentTimeMillis();

        System.out.println(beginTime);
        System.out.println(new Date() );
        Student ss = mapper.readValue(s,Student.class);
        System.out.println(mapper.writeValueAsString(students));

        System.out.println(mapper.writeValueAsString(map));

        Set<Integer> set = new HashSet<Integer>();
        set.add(33);
        set.add(64);

        set.add(student.getAge());
        set.add(33);


        System.out.println(mapper.writeValueAsString(set));

    }
}
