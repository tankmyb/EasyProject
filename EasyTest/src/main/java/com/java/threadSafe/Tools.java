package com.java.threadSafe;

public class Tools {

	public  static void update(Vo vo){
		//System.out.println(vo);
		vo.setName(Thread.currentThread().getName());
	}
	public static Vo create(){
		Vo vo = new Vo();
		vo.setName(Thread.currentThread().getName());
		return vo;
	}
}
