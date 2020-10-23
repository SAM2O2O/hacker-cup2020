package com.immomo.hacker.cup2020.demo2;

import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * @author SAM{an.guoyue254@gmail.com}
 * @description com.immomo.hacker.cup2020
 * @date 2020/10/23 12:04 PM
 */
public class RedisReader {

    public static final int[] READ_INDEX = new int[10];

    public static void read() {
        for (int i = 0; i < 10; i++) {

            final int fi = i;
            final String redisKey = "list-" + i;

            new Thread(new Runnable() {
                @Override
                public void run() {

                    List bufList = Main.RedisToListMap.get(redisKey);

                    // 10个连接
                    Jedis jedis = new Jedis(Main.RedisHost, Main.RedisPort);

                    while (true) {
                        String data = jedis.rpop(redisKey);

                        if (data != null) {
                            READ_INDEX[fi]++;
                            bufList.add(data);
                        } else {
                            Long llen = jedis.llen(redisKey);

                            if (llen == null || llen <= 0) {
                                return;
                            }
                        }
//                        System.out.println("Reader redisKey=" + redisKey + " count=" + READ_INDEX[fi] + " size=" + bufList.size());
                    }

                }
            }).start();

        }
    }

}
