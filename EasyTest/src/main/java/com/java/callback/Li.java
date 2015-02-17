package com.java.callback;

public class Li {

	public void handler(Callback callback,String question){
		//模拟解决问题的时间
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		System.out.println("问题："+question+"，答案：2");
		callback.callback("2");
	}
}
