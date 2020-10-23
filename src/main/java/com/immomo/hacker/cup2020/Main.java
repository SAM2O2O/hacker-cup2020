package com.immomo.hacker.cup2020;

import redis.clients.jedis.Jedis;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author SAM{an.guoyue254@gmail.com}
 * @description com.immomo.test.hacker
 * @date 2019-10-25 10:39
 */
public class Main {

    private static Pattern pattern = Pattern.compile("\\{\"momoId\":([0-9]+),\"lat\":([0-9\\.]+),\"lng\":([0-9\\.]+),\"sex\":\"([\\w]+)\"");

    private static ExecutorService executorService = Executors.newFixedThreadPool(4);

    private static AtomicInteger TOTAL_COUNTER = new AtomicInteger();

    private static AtomicBoolean IS_RUNNIng = new AtomicBoolean(false);


    private static final int TotalQueueSize = 10;
    private static final String RedisHost = "127.0.0.1";
    private static final int RedisPort = 6379;

    /**
     * @param args
     */
    public static void main(String[] args) {

        for (int i = 0; i < TotalQueueSize; i++) {


            final String redisKey = "list-" + i;


            new Thread(new Runnable() {
                @Override
                public void run() {
                    // 10个连接
                    Jedis jedis = new Jedis(RedisHost, RedisPort);
                }
            }).start();

        }

    }
    
}
