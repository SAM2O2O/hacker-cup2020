package com.immomo.hacker.cup2020;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author SAM{an.guoyue254@gmail.com}
 * @description com.immomo.test.hacker
 * @date 2019-10-25 10:39
 */
public class Main {

    public static final String RedisHost = "127.0.0.1";
    public static final int RedisPort = 6379;

    public static final LinkedList<String> EndDataList = new LinkedList<>();

    public static Map<String, List> RedisToListMap = new HashMap<>();

    /**
     * @param args
     */
    public static void main(String[] args) {

        RedisReader.read();

        RedisWriter.start();
    }

}
