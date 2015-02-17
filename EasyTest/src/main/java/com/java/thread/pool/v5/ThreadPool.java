package com.java.thread.pool.v5;


public class ThreadPool {
  public static ThreadPool pool = new ThreadPool();
	private boolean shutdown = false;
  public static ThreadArray threadArray;
  private ThreadPool(){}
	public static ThreadPool getInstance(int size) {
		threadArray = new ThreadArray(size);
		return pool;
	}

	public void execute(IJob job) {
		if (!shutdown) {
			synchronized (threadArray) {
				//System.out.println(threadArray.isEmpty());
				if (threadArray.isEmpty()) {
					try {
						threadArray.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			WorkThread t = threadArray.getNextWorkThread();
			//System.out.println(t+"===========ttttttt");
			t.wake(job);
		}else {
			System.out.println("此任务已被中止");
		}

	}


	public void shutdownNow() {
		shutdown = true;
	}
}
