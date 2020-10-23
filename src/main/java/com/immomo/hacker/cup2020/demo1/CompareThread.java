package com.immomo.hacker.cup2020.demo1;

public class CompareThread implements Runnable {

    public static final int[] COMPARE_INDEX = new int[10];

    @Override
    public void run() {
        int i = 0;
        while (i < 10000) {
            int index = findMinList();
            if (index < 0) {
                break;
            }

            // 新增到最小集序列中
            WriteThread.result[WriteThread.compareIndex] = ReadThread.REDIS_LIST[index][COMPARE_INDEX[index]];
            // 已完成比较自增1
            WriteThread.compareIndex = WriteThread.compareIndex + 1;
            COMPARE_INDEX[index] = COMPARE_INDEX[index] + 1;
        }
    }

    /**
     * 找出当前队列中最小的值
     *
     * @return
     */
    private int findMinList() {
        int finishCnt = 0;
        int listId = -1;
        String minValue = null;
        boolean[] compared = new boolean[10];

        while (finishCnt < 10) {
            for (int i = 0; i < 10; i++) {
                if (compared[i] || COMPARE_INDEX[i] >= 1000) {
                    finishCnt++;
                    continue;
                }

                if (COMPARE_INDEX[i] >= ReadThread.READ_INDEX[i]) {
                    // 当前redis list的数据还未读取出来
                    continue;
                }

                // 比较出最小值
                String currentStr = ReadThread.REDIS_LIST[i][COMPARE_INDEX[i]];
                if (minValue == null || compare(minValue, currentStr) > 0) {
                    minValue = currentStr;
                    listId = i;
                }

                finishCnt++;
                compared[i] = true;
            }
        }

        return listId;
    }

    private static int compare(String a, String b) {
        if (a.length() == b.length()) {
            return a.compareTo(b);
        }

        return a.length() - b.length();
    }
}
