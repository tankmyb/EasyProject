package com.java.thread.pool.v1;

public class Test {

	public static void main(String[] args) throws InterruptedException {
		ThreadPool p = new ThreadPool(10);
		for(int i=0;i<200;i++){
			IJob job1 = new Job1();
			p.execute(job1);
			if(i==40){
				p.shutdownNow();
			}
		}
		
		
		
		
		//Thread.sleep(1000L);
		p.shutdown();
	}
}
