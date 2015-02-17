package com.java.thread.pool.v5;

public class WorkThread extends Thread {
	public final static String CREATEING="createing";
	public final static String RUNNING="running";
	public final static String IDLING="idling";
	private IJob job;
	private boolean shutdown = false;
	private String workState;
  private ThreadArray threadArray;
  public WorkThread(ThreadArray threadArray){
  	this.threadArray = threadArray;
  }
	public String getWorkState() {
		return workState;
	}

	public void setWorkState(String workState) {
		this.workState = workState;
	}

	@Override
	public synchronized void run() {
		while (!shutdown) {
			if (job == null) {
				try {
					this.wait();
					this.workState = WorkThread.IDLING;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else {
				this.workState = WorkThread.RUNNING;
				System.out.println(Thread.currentThread().getName());
				job.execute(null);
				job = null;
				synchronized (threadArray) {
					threadArray.addWorkThread(this);
					threadArray.notifyAll();
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
