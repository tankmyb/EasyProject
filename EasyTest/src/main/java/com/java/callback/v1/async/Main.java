package com.java.callback.v1.async;

public class Main {

	public static void main(String[] args) {
		Boss boss = new Boss();
		Wang w = new Wang(boss);
		System.out.println("老板叫小王出方案");
		w.work();
		boss.doOther();
		
	}
}
