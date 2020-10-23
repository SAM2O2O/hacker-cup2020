package com.immomo.hacker.cup2020.demo2;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * @author SAM{an.guoyue254@gmail.com}
 * @description com.immomo.test.hacker
 * @date 2019-10-25 10:39
 */
public class Main {

    public static final String RedisHost = "127.0.0.1";
    public static final int RedisPort = 6379;

    public static Map<String, LinkedList<String>> RedisToListMap = new HashMap<>(10);

    private static void init() {
        for (int i = 0; i < 10; i++) {
            LinkedList<String> list = new LinkedList<>();
            RedisToListMap.put("list-" + i, list);
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {

        init();

        RedisReader.read();

        new Thread(new RedisWriter.WriterRunable()).start();

        RedisComparer.compare();
    }

}
