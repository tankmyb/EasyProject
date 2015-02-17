package com.socket.bio.message;

import java.util.concurrent.ConcurrentHashMap;

public class HandlerThread extends Thread{
  private static HandlerThread handler = new HandlerThread();
	private String message=null;
	private static ConcurrentHashMap<String, CallableThread> tMap = new ConcurrentHashMap<String, CallableThread>(); 
	private HandlerThread(){}
	public static HandlerThread getInstance(){
		return handler;
	}
	public synchronized void sendMessage(String message,CallableThread t,String sid){
		this.message = message;
		System.out.println(message+"===dffd==");
		notify();
		//tMap.put(sid, t);
	}
	public synchronized void run(){
		while(true){
			if(this.message == null){
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}else {
				try {
					System.out.println(message);
					Thread.sleep(2000L);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//CallableThread t = tMap.get("aa");
				//t.setReturnMsg("sdffffff");
				//tMap.remove("aa");
				message = null;
			}
		}
	}
	public static void main(String[] args) {
		HandlerThread handler = HandlerThread.getInstance();
		handler.start();
		//handler.sendMessage("sdsdsdsd", null, "");
	}
}
