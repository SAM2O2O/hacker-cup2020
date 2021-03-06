package com.immomo.hacker.cup2020.demo2;

import com.immomo.hacker.cup2020.demo1.ReadThread;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author SAM{an.guoyue254@gmail.com}
 * @description com.immomo.hacker.cup2020
 * @date 2020/10/23 12:04 PM
 */
public class RedisComparer {

    private static AtomicInteger total = new AtomicInteger();

    private static String[] compareDatas = new String[10];

    public static final int[] COMPARE_INDEX = new int[10];

    public static void compare() {

        // 标记是否比较完成，默认false
        boolean[] compared = new boolean[10];

        int first = 0;
        while (true) {
            int num = first;
            String data1 = null;
            // 先找到data1
            //# TODO 考虑最后一个
            for (; first < compareDatas.length; first++) {
                if (!compared[first]) {
                    num = first;
                    data1 = compareDatas[num];
                    break;
                }
            }

            for (int i = num + 1; i < compareDatas.length; i++) {

                if (data1 == null) {
                    compareDatas[num] = Main.RedisToListMap.get("list-" + num).poll();
                    break;
                }

                String data2 = compareDatas[i];
                if (data2 == null) {
                    if (compared[i]) {
                        i++;
                    } else {
                        compareDatas[i] = Main.RedisToListMap.get("list-" + i).poll();
                    }
                    continue;
                }
                if (compare(data1, data2) > 0) {
                    data1 = data2;
                    num = i;
                }
            }
            if (data1 != null) {
                COMPARE_INDEX[num]++;
                if (COMPARE_INDEX[num] >= 1000) {
                    compared[num] = true;
                }
                total.incrementAndGet();
                compareDatas[num] = Main.RedisToListMap.get("list-" + num).poll();
                RedisWriter.write(data1);

//                for (int ss = 0; ss < COMPARE_INDEX.length; ss++) {
//                    System.out.println("compare Index= " + ss + " num=" + COMPARE_INDEX[ss] + " total=" + total.get() + " left=" + Main.RedisToListMap.get("list-" + ss).size() + " finish=" + compared[ss]);
//                }
            }


            if (total.get() >= 10000) {
                break;
            }
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
