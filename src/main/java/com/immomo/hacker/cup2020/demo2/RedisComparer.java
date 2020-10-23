package com.immomo.hacker.cup2020.demo2;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author SAM{an.guoyue254@gmail.com}
 * @description com.immomo.hacker.cup2020
 * @date 2020/10/23 12:04 PM
 */
public class RedisComparer {

    private static AtomicInteger total = new AtomicInteger();
    private static String[] compareDatas = new String[10];

    public static void compare() {

        while (true) {
            String data1 = compareDatas[0];
            int num = 0;
            for (int i = 1; i < compareDatas.length; i++) {
                String data2 = compareDatas[i];
                if (data1 == null) {
                    num = i;
                    data1 = data2;
                }
                if (data2 == null) {
                    continue;
                }
                if (compare(data1, data2) > 0) {
                    data1 = data2;
                    num = i;
                }
            }
            compareDatas[num] = Main.RedisToListMap.get("list-" + num).poll();
            if (data1 != null) {
                total.incrementAndGet();
                RedisWriter.write(data1);
            }

            System.out.println("total=" + total);
            if (total.get() >= 10000) {
                break;
            }
        }

        //check finish
        while (RedisWriter.finish()) {
            return;
        }
    }


    /**
     * a > b  正数
     * a < b 负数
     *
     * @param a
     * @param b
     * @return
     */
    private static int compare(String a, String b) {
        if (a.length() == b.length()) {
            return a.compareTo(b);
        }

        return a.length() - b.length();
    }
}
