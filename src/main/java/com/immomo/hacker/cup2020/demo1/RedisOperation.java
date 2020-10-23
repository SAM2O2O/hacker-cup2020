package com.immomo.hacker.cup2020.demo1;

public class RedisOperation {

	private Integer value = 0;
	
	public void write(String str) {
		// 写入结果集
		//System.out.println(str);
	}
	
	public synchronized String read(int index) {
		synchronized (value) {
			System.out.println(value);
			// 从指定list 读取一条数据
			return "" + (value ++);
		}
	}
}
