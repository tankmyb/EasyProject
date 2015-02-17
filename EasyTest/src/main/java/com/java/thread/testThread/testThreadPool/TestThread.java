package com.java.thread.testThread.testThreadPool;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


public class TestThread {
	Timer timer = new Timer(true);
	private ConcurrentHashMap<String, ReceiveThread> tMap = new ConcurrentHashMap<String, ReceiveThread>();
	public void startTimer(){
		timer.schedule(new java.util.TimerTask() {
			public void run() {
				System.out.println("=============ewew");
				if(!tMap.isEmpty()){
					Date d = new Date();
					Iterator<Map.Entry<String,ReceiveThread>> it = tMap.entrySet().iterator();
					while(it.hasNext()){
						Map.Entry<String, ReceiveThread> entry=it.next();
						ReceiveThread rt = entry.getValue();
						if(d.getTime()-rt.getDate().getTime()>5){
							String key = entry.getKey();
							rt.notifyThread();
							tMap.remove(key);
						}
					}
				}
				}
		}, 0,  100);
	}
	public void add(String sid,ReceiveThread rt){
		tMap.put(sid, rt);
	}
	public static void main(String[] args)  {
		try {
			Thread.sleep(10000L);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		TestThread tt = new TestThread();
		//tt.startTimer();
		System.out.println("============sdsd====155");
		//ExecutorService es = Executors.newCachedThreadPool();
		ThreadPoolManager es = ThreadPoolManager.getInstance();
		int i=0;
		for(int j=0;j<1000000;j++){
			ReceiveThread c = new ReceiveThread(new Date()); 
			String sid = UUID.randomUUID().toString();
			tt.add(sid,c);
			Future<String> future1 = es.submit(c);
			System.out.println("   run:"+i++);
			String f = null;
			long time = 50L;
			if(j%2==0){
				time=10L;
			}
			try {
				f = future1.get(time, TimeUnit.MILLISECONDS);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TimeoutException e) {
				// TODO Auto-generated catch block
				future1.cancel(true);
				//c.notifyThread();
				tt.tMap.remove(sid);
				e.printStackTrace();
			}
			
			try {
				
				System.out.println("return:"+f+"   run:"+tt.tMap.size());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//es.shutdown();
		
	}
}
