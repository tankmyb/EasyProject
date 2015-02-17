package com.java.thread.pool.v1;

import java.util.Stack;
import java.util.concurrent.CountDownLatch;

public class ThreadPool {
	class WorkThread extends Thread {
		public  Integer CREATEING=0;
		public  Integer RUNNING=1;
		public  Integer IDLING=2;
		private IJob job;
		private boolean shutdown = false;
		private Integer state;
    public void setState(Integer state){
    	this.state = state;
    }
		@Override
		public synchronized void run() {
			while (!shutdown) {
				if (job == null) {
					try {
						this.wait();
						this.state = this.IDLING;
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} else {
					this.state = this.RUNNING;
					System.out.println(Thread.currentThread().getName());
					job.execute(null);
					job = null;
					countDownLatch.countDown();
					synchronized (pool) {
						pool.push(this);
						pool.notifyAll();
					}

				}
			}

		}

		public synchronized void wake(IJob job) {
			this.job = job;
			this.notify();

		}

		public synchronized void shutdown() {
			this.shutdown = true;
			this.notify();
		}
	}

	private int poolSize;
	private Stack<WorkThread> pool;
	private boolean shutdown = false;
	private CountDownLatch countDownLatch;

	public ThreadPool(int size) {
		this.poolSize = size;
		pool = new Stack<WorkThread>();
		pool.ensureCapacity(size);
		for (int i = 0; i < size; i++) {
			WorkThread t = new WorkThread();
			t.setState(t.CREATEING);
			pool.push(t);
			t.start();
		}
		countDownLatch = new CountDownLatch(200);
	}

	public void execute(IJob job) {
		if (!shutdown) {
			synchronized (pool) {
				if (pool.isEmpty()) {
					try {
						pool.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			WorkThread t = pool.pop();
			t.wake(job);
		}else {
			System.out.println("此任务已被中止");
		}

	}

	

	public void shutdown() {
		shutdown = true;
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(pool.size() + "=======size");
		for (int i = 0; i < poolSize; i++) {
			pool.pop().shutdown();
		}
	}

	public void shutdownNow() {
		shutdown = true;
	}
}
