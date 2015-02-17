package com.java.thread.testThread.testThreadPool;

import java.util.Date;
import java.util.concurrent.Callable;

public class ReceiveThread implements Callable<String>{
	private Date date;
	public ReceiveThread(Date d){
		this.date =d;
	}
	public Date getDate() {
		return date;
	}
	public synchronized void notifyThread(){
		this.notify();
	}
	public synchronized String call() throws Exception {
	  while(true){
	  	wait();
	  	return "true";
	  }
}

}
