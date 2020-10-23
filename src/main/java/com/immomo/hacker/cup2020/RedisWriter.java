package com.immomo.hacker.cup2020;

import com.sun.tools.javac.util.StringUtils;
import redis.clients.jedis.Jedis;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author SAM{an.guoyue254@gmail.com}
 * @description com.immomo.hacker.cup2020
 * @date 2020/10/23 12:04 PM
 */
public class RedisWriter {

    private static AtomicBoolean RUNNING = new AtomicBoolean(true);
    private static Jedis jedis = new Jedis(Main.RedisHost, Main.RedisPort);
    private static final String redisKey = "result-key";

    public static void write() {

        new Thread(new Runnable() {
            @Override
            public void run() {

                while (true) {
                    String data = Main.EndDataList.pop();
                    if (data != null) {
                        jedis.append(redisKey, data);
                    }

                    if (!RUNNING.get() && Main.EndDataList.isEmpty()) {
                        return;
                    }
                }

            }
        });

    }

    public static void end() {
        RUNNING.getAndSet(false);
    }
}
