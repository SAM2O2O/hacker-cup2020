package com.immomo.hacker.cup2020;

import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * @author SAM{an.guoyue254@gmail.com}
 * @description com.immomo.hacker.cup2020
 * @date 2020/10/23 12:04 PM
 */
public class RedisReader {
    private static final int TotalQueueSize = 10;

    public static void read() {
        for (int i = 0; i < TotalQueueSize; i++) {


            final String redisKey = "list-" + i;

            new Thread(new Runnable() {
                @Override
                public void run() {

                    List bufList = Main.RedisToListMap.get(redisKey);

                    int count = 0;
                    // 10个连接
                    Jedis jedis = new Jedis(Main.RedisHost, Main.RedisPort);

                    String data = jedis.rpop(redisKey);

                    if (data != null && bufList.size() < 5) {
                        count++;
                        bufList.add(data);
                    }

                }
            }).start();

        }
    }

}
