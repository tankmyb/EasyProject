package com.java.thread.pool.v5;

public class ThreadArray {
  private WorkThread[] pool;
	public ThreadArray(int size){
		pool = new WorkThread[size];
		for (int i = 0; i < size; i++) {
			WorkThread t = new WorkThread(this);
			t.setWorkState(WorkThread.CREATEING);
			pool[i]=t;
			t.start();
		}
	}
	public synchronized boolean isEmpty(){
		for(WorkThread wt:pool){
			if(wt.getWorkState().equals(WorkThread.CREATEING) || wt.getWorkState().equals(WorkThread.IDLING)){
				return false;
			}
		}
		return true;
	}
	public synchronized WorkThread getNextWorkThread(){
		for(WorkThread wt:pool){
			if(wt.getWorkState().equals(WorkThread.CREATEING) || wt.getWorkState().equals(WorkThread.IDLING)){
				return wt;
			}
		}
		return null;
	}
	public synchronized void addWorkThread(WorkThread workThread){
		for(WorkThread wt:pool){
			//System.out.println(wt+"===="+workThread+"====="+(wt == workThread));
			if(wt == workThread){
				wt.setWorkState(WorkThread.IDLING);
				break;
			}
		}
	}
}
