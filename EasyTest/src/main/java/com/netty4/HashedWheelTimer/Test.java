package com.netty4.HashedWheelTimer;

import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import io.netty.util.Timer;
import io.netty.util.TimerTask;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Test {

	public static void main(String[] args) {
		Timer timer = new HashedWheelTimer();
		for(int i=0;i<10000;i++){
			timer.newTimeout(new TimerTask() {
	            public void run(Timeout timeout) throws Exception {
	                System.out.println("timeout 5="+new Date());
	            }
	        }, 5, TimeUnit.SECONDS);
		}
	    }
}
