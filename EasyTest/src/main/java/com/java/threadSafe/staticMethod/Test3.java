package com.java.threadSafe.staticMethod;

import com.java.threadSafe.Tools;
import com.java.threadSafe.Vo;

public class Test3 extends Thread {
	private Vo vo;

	public Test3(Vo vo) {
		this.vo = vo;
	}

	public void run() {
		vo.setName(Thread.currentThread().getName());
		Tools.update(vo);
			try {
				Thread.sleep(100L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (!vo.getName().equals(vo.getName())) {
				System.err.println("not equals");
			}
			//System.out.println("name2:" + vo.getName());
		
	}

	public static void main(String[] args) {
		Vo vo = new Vo();
		for (int i = 0; i < 100; i++) {
			
			new Test3(vo).start();
		}
	}

}
