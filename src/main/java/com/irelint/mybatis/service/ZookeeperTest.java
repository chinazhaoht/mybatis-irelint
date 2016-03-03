package com.irelint.mybatis.service;

/**
 * Created by dell on 2016/2/29.
 */
@SuppressWarnings("checked")
public class ZookeeperTest {

    private static class Zookeeper{
        public Zookeeper(){
            System.out.println("hello");
            String s = new String("hello");
            System.out.println(s);
            s = s + "nihao";
            System.out.println(s);

            StringBuffer ss = new StringBuffer("sfadfa");
            System.out.println(ss);
            ss.append("sfafafadfa");
            System.out.println(ss);
            System.out.println(this.getClass().getClass().getClass().getClass());
        }
    }

    public static void main(String[] args){
        new Zookeeper();
        System.out.println("Hello World!");
    }
}
