package com.immomo.hacker.cup2020.demo1;

public class ReadThread implements Runnable {

	public static final String[][] REDIS_LIST = new String[10][1000];
	public static final int[] READ_INDEX = new int[10];

	private int[] OPERATE_LIST;		//该线程需要操作的redis list
	
	private RedisOperation redisOperation = new RedisOperation();
	
	public ReadThread(int ... redisId) {
		OPERATE_LIST = redisId;
	}
	
	@Override
	public void run() {
		int maxSize = OPERATE_LIST.length * 1000;
		int readSize = 0;
		while (readSize < maxSize) {
			for (int id : OPERATE_LIST) {
				if (READ_INDEX[id] >= 1000) {
					//该list已经读取完毕
					continue;
				}
				
				//将值读取放入读取list中
				REDIS_LIST[id][READ_INDEX[id]] = redisOperation.read(id);
				//读取索引自增
				READ_INDEX[id] = READ_INDEX[id] + 1;
				
				readSize ++;
			}
		}
	}
}
