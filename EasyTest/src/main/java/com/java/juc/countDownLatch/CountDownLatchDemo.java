package com.java.juc.countDownLatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/*
 * 从名字可以看出，CountDownLatch是一个倒数计数的锁，
当倒数到0时触发事件，也就是开锁，其他人就可以进入了。
在一些应用场合中，需要等待某个条件达到要求后才能做后面的事情；同时当线程都完成后也会触发事件，以便进行后面的操作。


CountDownLatch最重要的方法是countDown()和await()，前者主要是倒数一次，后者是等待倒数到0，如果没有到达0，就只有阻塞等待了。


下面的例子简单的说明了CountDownLatch的使用方法，模拟了100米赛跑，10名选手已经准备就绪，只等裁判一声令下。当所有人都到达终点时，比赛结束

 */
public class CountDownLatchDemo {
	private static final int PLAY_AMOUNT = 10;

	public static void main(String[] args) {

		/*
		 * 比赛开始：只要裁判说开始，那么所有跑步选手就可以开始跑了
		 */
		CountDownLatch begin = new CountDownLatch(1);

		/*
		 * 每个队员跑到末尾时，则报告一个到达，所有人员都到达时，则比赛结束
		 */
		CountDownLatch end = new CountDownLatch(PLAY_AMOUNT);
		Player[] plays = new Player[PLAY_AMOUNT];
		for (int i = 0; i < PLAY_AMOUNT; i++) {
			plays[i] = new Player(i + 1, begin, end);
		}
		ExecutorService exe = Executors.newFixedThreadPool(PLAY_AMOUNT);
		for (Player p : plays) {// 各就各位
			exe.execute(p);
		}
		System.out.println("比赛开始");
		begin.countDown();// 宣布开始
		try {
			end.await();// 等待结束
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			System.out.println("比赛结束");
		}

		// 注意：此时main线程已经要结束了，但是exe线程如果不关闭是不会结束的
		exe.shutdown();
	}

}

class Player implements Runnable {

	private int id;

	private CountDownLatch begin;

	private CountDownLatch end;

	public Player(int id, CountDownLatch begin, CountDownLatch end) {
		super();
		this.id = id;
		this.begin = begin;
		this.end = end;
	}

	public void run() {
		try {
			begin.await();// 必须等到裁判countdown到0的时候才开始
			Thread.sleep((long) (Math.random() * 100));// 模拟跑步需要的时间
			System.out.println("Play " + id + " has arrived. ");

		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			end.countDown();// 向评委报告跑到终点了
		}
	}

}
