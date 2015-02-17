package com.java.threadSafe.singleton;

import com.jackjson.ResolveUnit;
import com.java.threadSafe.Child;
import com.java.threadSafe.Vo;

public class Service {

	public Manager manager = Manager.getInstance();
	
	public void handler(String message){
		new ServiceThread(message).start();
	}
	class ServiceThread extends Thread{
		private String message;
		public ServiceThread(String message){
			this.message = message;
		}
		public void run(){
			Vo vo = ResolveUnit.resolve(message,Vo.class);
			vo.setClassName(Thread.currentThread().getName());
			manager.find1(vo);
			System.out.println("className:"+vo.getClassName()+"  threadName:"+Thread.currentThread().getName()+"  name:"+vo.getName());
			//if(type.equals("find1")){
				
			//}
		}
	}
	public static void main(String[] args) {
		Service service = new Service();
		for(int i=0;i<100;i++){
			Vo vo = new Vo();
			vo.setId(1);
			vo.setName("namemmm"+i);
			
			Child child = new Child();
			child.setClassess("c1");
			child.setSex("male");
			vo.setChild(child);
			service.handler(ResolveUnit.getJsonStr(vo));
		}
	}
}
