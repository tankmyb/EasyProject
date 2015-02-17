package com.java.thread.pool.v6;

import java.util.Stack;

public class ThreadPool {
	class WorkThread extends Thread {
		public  Integer CREATEING=0;
		public  Integer RUNNING=1;
		public  Integer IDLING=2;
		private IJob job;
		private boolean shutdown = false;
		private Integer workState;
    public void setWorkState(Integer state){
    	this.workState = state;
    }
    public Integer getWorkState(){
    	return this.workState;
    }
		@Override
		public synchronized void run() {
			while (!shutdown) {
				if (job == null) {
					try {
						this.wait();
						//this.workState = this.IDLING;
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} else {
					this.workState = this.RUNNING;
					System.out.println(Thread.currentThread().getName());
					job.execute(null);
					job = null;
					this.workState = this.IDLING;
					synchronized (pool) {
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

	private Stack<WorkThread> pool;
	private boolean shutdown = false;

	public ThreadPool(int size) {
		pool = new Stack<WorkThread>();
		pool.ensureCapacity(size);
		for (int i = 0; i < size; i++) {
			WorkThread t = new WorkThread();
			t.setWorkState(t.CREATEING);
			pool.push(t);
			t.start();
		}
	}
  public synchronized boolean isFull(){
  	for(WorkThread wt:pool){
  		if(wt.getWorkState().intValue()==0 || wt.getWorkState().intValue()==2){
  			return false;
  		}
  	}
  	return true;
  }
  
  public synchronized WorkThread getNextWorkThread(){
  	for(WorkThread wt:pool){
  		if(wt.getWorkState().intValue()==0 || wt.getWorkState().intValue()==2){
  			return wt;
  		}
  	}
  	return null;
  }
 
	public void execute(IJob job) {
		//System.out.println(isFull());
		if (!shutdown) {
			synchronized (pool) {
				if (isFull()) {
					try {
						pool.wait();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

			WorkThread t = getNextWorkThread();
			//if(t != null){
				t.wake(job);
			//}else {
				//System.out.println("==========11");
			//}
		
			
		}else {
			System.out.println("此任务已被中止");
		}
	}

	
	

	public void shutdownNow() {
		shutdown = true;
	}
}
