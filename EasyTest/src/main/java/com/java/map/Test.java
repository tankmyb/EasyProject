package com.java.map;

public class Test {
  static int index=0;
	public static void main(String[] args) {
		for(int i=0;i<1000;i++){
			new Thread(){
				public void run(){
					test();
				}
			}.start();
			index++;
		}
	}
	public static void test(){
		JsonMsg msg = new JsonMsg(true);
		//if(index%2==0){
		msg.setSuccessMsg("b"+index);
			msg.setFailMsg("a"+index);
		//}else {
			
		//}
		//System.out.println(msg.getMap());
			try {
				Thread.sleep(1000L);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(!msg.getState().booleanValue()){
				System.err.println("=========1");
			}else {
				System.err.println("2222222222222222");
			}
			//System.err.println(msg.getState().booleanValue());
	}
}
