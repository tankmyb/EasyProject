 package com.socket.nio.second.wr2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Random;

import com.socket.nio.second.CharsetUtils;

public class AsyncServer implements Runnable{ 
	public static final int BLOCK = 10;
	private ByteBuffer r_buff = ByteBuffer.allocate(BLOCK);
	 private ByteBuffer w_buff = ByteBuffer.allocate(BLOCK);
	 private static int port = 8848;
	 
	 public AsyncServer(){
	  new Thread(this).start();
	 }
	 
	 public void run(){    
	  try{
	   //生成一个侦听端
	   ServerSocketChannel ssc = ServerSocketChannel.open();
	   //将侦听端设为异步方式
	   ssc.configureBlocking(false);
	   //生成一个信号监视器
	   Selector s = Selector.open();
	   //侦听端绑定到一个端口
	   ssc.socket().bind(new InetSocketAddress(port));
	   //设置侦听端所选的异步信号OP_ACCEPT
	   ssc.register(s,SelectionKey.OP_ACCEPT);
	   System.out.println("echo server has been set up ......");
	 
	   while(true){
	    int n = s.select();
	    if (n == 0) {//没有指定的I/O事件发生
	     continue;
	    }     
	    Iterator it = s.selectedKeys().iterator();     
	    while (it.hasNext()) {
	     SelectionKey key = (SelectionKey) it.next();
	     if (key.isAcceptable()) {//侦听端信号触发
	      ServerSocketChannel server = (ServerSocketChannel) key.channel();
	      //接受一个新的连接
	      SocketChannel sc = server.accept();
	      sc.configureBlocking(false);
	      //设置该socket的异步信号OP_READ:当socket可读时，

	     //触发函数DealwithData();
	      sc.register(s,SelectionKey.OP_READ);
	     }   
	     else if (key.isReadable()) {//某socket可读信号
	    	 System.out.println("来了");
	      DealwithData(key);
	     }     
	     it.remove();
	    }
	   }
	  }
	  catch(Exception e){
	   e.printStackTrace(); 
	  }
	 }
	  
	 public void DealwithData(SelectionKey key) throws IOException{
	  int count;
	  //由key获取指定socketchannel的引用
	  SocketChannel sc = (SocketChannel)key.channel();
	  w_buff.clear();
	  r_buff.clear();
	  boolean flag = false;
	  //读取数据到r_buff
	  while((count = sc.read(r_buff))>0){
	  //确保r_buff可读
	  r_buff.flip();
	  
	  //将r_buff内容拷入w_buff  
	  w_buff.put(r_buff);
	  w_buff.flip();
	  //将数据返回给客户端
	  String receive = CharsetUtils.UTF8.decode(w_buff).toString();
	  System.out.println("count:"+count+",收到："+receive);
	  if(!receive.equals("end")){
		  w_buff.flip();
		  EchoToClient(sc);
	  }else {
		  flag = true;
		  break;
	  }
	 
	  w_buff.clear();
	  r_buff.clear();
	  }
	  if(flag){
		  sc.close();
	  }
	 }
	 
	 private void EchoToClient(SocketChannel sc) throws IOException{
		 /*Random random = new Random();

         int a = random.nextInt();
         System.out.println(a%2+"===================aaaaaa");
         if(a%2==0){
        	 try {
				Thread.sleep(1000L);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
         }*/
	  while(w_buff.hasRemaining()){
	   sc.write(w_buff);
	  }
	 }
	 
	 public static void main(String args[]){
	  new AsyncServer();
	 }


}
