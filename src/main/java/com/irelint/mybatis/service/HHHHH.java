package com.irelint.mybatis.service;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by dell on 2016/3/3.
 */
public class HHHHH {

    static final String connectingString = "120.27.127.2:2181,120.27.127.2:3181";

    static CuratorFramework client = null;
    public static void main(String[] args) throws Exception {

        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000,3);

        client = CuratorFrameworkFactory.builder()
                .connectString(connectingString)
                .sessionTimeoutMs(5000)
                .retryPolicy(retryPolicy)
                .namespace("base")
                .build();
        client.start();

        final CountDownLatch down = new CountDownLatch(1);
        for(int i = 0; i <= 10; i++){
            new Thread(new Runnable() {
                public void run() {
                    try {
                        down.await();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss:SSS");
                    String orderNo = sdf.format(new Date());
                    System.err.println("生成的订单号是:"+orderNo);
                }
            }).start();
        }
     down.countDown();


    }

    public void one() throws Exception {
        String path = "/animal";
        client.create().forPath(path,"pig".getBytes());
        final NodeCache cache = new NodeCache(client,path,false);
        cache.start(true);
        cache.getListenable().addListener(new NodeCacheListener() {
            public void nodeChanged() throws Exception {
                System.out.println("Node data update ,new data "+new String(cache.getCurrentData().getData()));
            }
        });

        client.setData().forPath(path,"unit".getBytes());
        Thread.sleep(1000);
        client.delete().deletingChildrenIfNeeded().forPath(path);
        Thread.sleep(Integer.MAX_VALUE);
    }


    //master选举
    public void two() throws InterruptedException {
        LeaderSelector selector = new LeaderSelector(client, "/master_path", new LeaderSelectorListenerAdapter() {
            public void takeLeadership(CuratorFramework curatorFramework) throws Exception {
                System.out.println("成为Master角色");
                Thread.sleep(3000);
                System.out.println("完成Master操作，释放Master权利");
            }
        });

        selector.autoRequeue();
        selector.start();
        Thread.sleep(Integer.MAX_VALUE);
    }
}
