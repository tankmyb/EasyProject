package com.java.thread.sync;

public class SyncThreadTest {
	static int count;
	 static final int CIRCLE_COUNT=10000;
	 static  long start= System.currentTimeMillis();
	 public static void main(String[] args) { 
	  for(int i=0;i<CIRCLE_COUNT;i++)
	   test(i);
	  
	 }
	 
	 public static void test(final int seed){
	  Thread thread=new Thread(){ 
	   public void run(){
	    int radom=radom(seed);
	    count++;
	    if(count==CIRCLE_COUNT)
	     System.out.println("time:"+(System.currentTimeMillis()-start)+"ms");
	   }
	  };
	   thread.start();
	 }
	 
	 public static synchronized int radom(int seed){
	  long result = 0;
	  if (seed != 0) {
	   double d = Math.random();
	   String temp = d + "";
	   // System.out.println("temp:" + temp); 
	   int len = temp.length() - 2;// 去掉开头两位
	   d = d * Math.pow(10, len);
	   // System.out.println("d:" + d);
	   result = (long) d % seed;
	  }
	  return (int) result;
	 }

}
