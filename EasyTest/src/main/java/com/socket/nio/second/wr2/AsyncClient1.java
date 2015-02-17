package com.socket.nio.second.wr2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class AsyncClient1 {
	private SocketChannel sc;
	 private final int MAX_LENGTH = 1024;
	 private ByteBuffer r_buff = ByteBuffer.allocate(MAX_LENGTH);
	 private ByteBuffer w_buff = ByteBuffer.allocate(MAX_LENGTH);
	 private static String host ="127.0.0.1";
	 private static int port = 8848;
	 
	 public AsyncClient1() throws Exception{
	  try {
	   InetSocketAddress addr = new InetSocketAddress(host,port);
	   //生成一个socketchannel
	   sc = SocketChannel.open();
	         
	   //连接到server
	   sc.connect(addr);
	   while(!sc.finishConnect())
	    ;  
	   System.out.println("connection has been established!...");
	    
	   while(true){
	    //回射消息
	    String echo = null;
	    try{
	     System.err.println("Enter msg you'd like to send:  ");
	     BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	     Scanner scanner=new Scanner(System.in);
	     echo = scanner.nextLine();
	     //输入回射消息
	     //echo = br.readLine();
	     
	     //把回射消息放入w_buff中    
	     w_buff.clear();
	     w_buff.put(echo.getBytes());
	     w_buff.flip();
	    }catch(Exception ioe){
	     System.err.println("sth. is wrong with br.readline() ");
	    }    
	  
	    //发送消息
	    while(w_buff.hasRemaining())
	     sc.write(w_buff);
	    w_buff.clear();    
	    if(echo.equals("end")){
	    	 break;
	     }
	    //进入接收状态
	    Rec();
	    //间隔1秒
	    Thread.currentThread().sleep(1000);
	   }  
	  }catch(IOException ioe){
	   ioe.printStackTrace();
	  }
	  catch(InterruptedException ie){
	   ie.printStackTrace();
	  }finally{
	  	sc.close();
	  }
	 }
	 ////////////
	 //读取server端发回的数据，并显示
	 public void Rec() throws IOException{
	  int count;
	  r_buff.clear(); 
	  count=sc.read(r_buff);
	  r_buff.flip();  
	  byte[] temp = new byte[r_buff.limit()];
	  r_buff.get(temp);
	  System.out.println("reply is " + count +" long, and content is: " + new String(temp));
	 }
	 
	 public static void main(String args[]) throws Exception{
	  new AsyncClient1();
	 }


}
