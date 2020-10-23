package com.immomo.hacker.cup2020.demo1;

public class Test {

	public static void main(String[] args) {
		Thread r1 =new Thread(new ReadThread(0, 1, 2));
		Thread r2 =new Thread(new ReadThread(3, 4, 5));
		Thread r3 =new Thread(new ReadThread(6, 7, 8, 9));
		
		r1.start();
		r2.start();
		r3.start();
		
		Thread c = new Thread(new CompareThread());
		c.start();
		
		Thread w = new Thread(new WriteThread());
		w.start();
	}
}
