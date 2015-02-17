package com.java.threadSafe.singleton;

import com.java.threadSafe.Vo;

public class Manager {

	private static Manager manager = new Manager();
	private Vo vo1 = new Vo();
	private Manager(){
		
	}
	public static Manager getInstance(){
		return manager;
	}
	public Vo find1(Vo vo){
		vo1.setName(vo.getName());
		for(int i=0;i<20;i++){
			vo1.setName(vo1.getName()+i);
			try {
				Thread.sleep(100L);
				System.out.println(vo.getClassName());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return vo1;
	}
	public Vo find2(Vo vo){
		for(int i=0;i<100;i++){
			vo.setName(vo.getName()+i);
			try {
				Thread.sleep(100L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return vo;
	}
}
