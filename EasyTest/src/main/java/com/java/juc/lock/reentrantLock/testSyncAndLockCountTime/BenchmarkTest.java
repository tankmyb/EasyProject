package com.java.juc.lock.reentrantLock.testSyncAndLockCountTime;

import java.util.concurrent.CyclicBarrier;

public class BenchmarkTest {
	private Counter counter;

	private CyclicBarrier barrier;

	private int threadNum;

	public BenchmarkTest(Counter counter, int threadNum) {
		this.counter = counter;
		barrier = new CyclicBarrier(threadNum + 1); // 关卡计数=线程数+1
		this.threadNum = threadNum;
	}

	public static void main(String args[]) {
		//new BenchmarkTest(new SynchronizeBenchmark(), 5000).test();
		new BenchmarkTest(new ReentrantLockBeanchmark(), 5000).test();
		// new BenchmarkTest(new ReentrantLockBeanchmark(), 5000).test();
	}

	public void test() {
		try {
			for (int i = 0; i < threadNum; i++) {
				new TestThread(counter).start();
			}
			long start = System.currentTimeMillis();
			barrier.await(); // 等待所有任务线程创建
			barrier.await(); // 等待所有任务计算完成
			long end = System.currentTimeMillis();
			System.out.println("count value:" + counter.getValue());
			System.out.println("花费时间:" + (end - start) + "毫秒");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	class TestThread extends Thread {
		private Counter counter;

		public TestThread(final Counter counter) {
			this.counter = counter;
		}

		public void run() {
			try {
				barrier.await();
				for (int i = 0; i < 100; i++)
					counter.increment();
				barrier.await();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

}
