package com.immomo.hacker.cup2020.demo1;

public class WriteThread implements Runnable {

    private static int LENGTH = 32768;

    public static String[] result = new String[10001];
    public static int compareIndex;   //当前比较好的元素索引
    private static int writeIndex;       //完成写入的元素索引
    private RedisOperation redisOperation = new RedisOperation();

    @Override
    public void run() {
        while (writeIndex < 10000) {
            int readySize = compareIndex - writeIndex;
            if (readySize <= 0) {
                continue;
            }

            writeIndex += readySize;
            String str = combine(writeIndex, readySize);
            redisOperation.write(str);
        }
    }

    private String combine(int startIndex, int size) {
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
