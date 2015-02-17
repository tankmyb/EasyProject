package com.java.callback;

/**
 * 小王问小李问题
 * @author a
 *
 */
public class Wang implements Callback{

	private Li li;
	
	public Wang(Li li){
		this.li = li;
	}
	public void askQuestion(final String question){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				li.handler(Wang.this, question);
				
			}
		}).start();
		//不阻塞，小王干自己的事去了
		play();
	}
	public void play(){
		System.out.println("小王去玩了");
	}
	@Override
	public void callback(String result) {
		System.out.println("小李回答的答案是:"+result);
		
	}

	public static void main(String[] args) {
		Li li = new Li();
		Wang wang = new Wang(li);
		wang.askQuestion("1+1=?");
	}
}
