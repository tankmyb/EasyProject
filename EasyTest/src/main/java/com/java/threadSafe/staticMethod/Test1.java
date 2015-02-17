package com.java.threadSafe.staticMethod;

import com.java.threadSafe.Tools;
import com.java.threadSafe.Vo;

public class Test1 extends Thread {
	private Vo vo;

	public Test1(Vo vo) {
		this.vo = vo;
	}

	public void run() {
		//synchronized (vo) {
			Tools.update(vo);
			//System.out.println("name1:" + vo.getName());
			try {
				Thread.sleep(100L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (!vo.getName().equals(Thread.currentThread().getName())) {
				System.err.println("not equals");
			}
			//System.out.println("name2:" + vo.getName());
		//}
		
	}

	public static void main(String[] args) {
		Vo vo = new Vo();
		for (int i = 0; i < 100; i++) {
			new Test1(vo).start();
		}
	}

}
