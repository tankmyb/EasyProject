package com.socket.bio.message;

import java.util.concurrent.Callable;

public class CallableThread implements Callable<String> {
  private String returnMsg = null;
  public synchronized void setReturnMsg(String returnMsg){
  	this.returnMsg = returnMsg;
  	this.notify();
  }
  public String getReturnMsg(){
  	
  	return returnMsg;
  }
public synchronized String call() {
	while (true) {
		if (returnMsg != null) {
			return returnMsg;
			
		} else{
			try {
					wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

   
}
