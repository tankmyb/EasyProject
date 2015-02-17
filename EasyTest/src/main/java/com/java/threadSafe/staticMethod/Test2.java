package com.java.threadSafe.staticMethod;

import com.java.threadSafe.Tools;
import com.java.threadSafe.Vo;

public class Test2 extends Thread {
	private Vo vo;

	public Test2(Vo vo) {
		this.vo = vo;
	}

	public void run() {
		vo.setName(Thread.currentThread().getName());
		Vo vo1 = Tools.create();
			try {
				Thread.sleep(100L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (!vo1.getName().equals(vo.getName())) {
				System.err.println("not equals");
			}
			//System.out.println("name2:" + vo.getName());
		
	}

	public static void main(String[] args) {
		
		for (int i = 0; i < 100; i++) {
			Vo vo = new Vo();
			new Test2(vo).start();
		}
	}

}
