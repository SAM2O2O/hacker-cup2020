package com.immomo.hacker.cup2020.demo2;

import redis.clients.jedis.Jedis;

/**
 * @author SAM{an.guoyue254@gmail.com}
 * @description com.immomo.hacker.cup2020
 * @date 2020/10/23 12:04 PM
 */
public class RedisWriter {

    private static Jedis jedis = new Jedis(Main.RedisHost, Main.RedisPort);
    private static final String redisKey = "result-key";

    private static final String[] result = new String[10001];
    // 已经写入的index
    public static int currentIndex = 0;
    // 最新写入的index
    private static int nextIndex = 0;

    private static final int LENGTH = 32768;

    public static void write(String data) {
        result[nextIndex] = data;
        nextIndex++;
    }

    public static boolean finish() {
        if (currentIndex >= 10000) {
            return true;
        }
        return false;
    }

    static class WriterRunable implements Runnable {

        @Override
        public void run() {
            while (currentIndex <= 10000) {
                int writeSize = nextIndex - currentIndex;
                String result = combine(currentIndex, writeSize);
                currentIndex += writeSize;
                System.out.println("Writer index=" + currentIndex);
                jedis.append(redisKey, result);
            }
        }

    }

    private static String combine(int startIndex, int size) {
        if (size == 1) {
            return result[startIndex];
        }

        StringBuilder sb = new StringBuilder(size * LENGTH);
        for (int i = 0; i < size; i++) {
            sb.append(result[startIndex + i]);
        }
        return sb.toString();
    }

}
