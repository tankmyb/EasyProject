package com.mina.v1;

import java.net.InetSocketAddress;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

public class TimeClient {

	public static void main(String[] args) throws InterruptedException {
		 //Create TCP/IP connection  
    NioSocketConnector connector = new NioSocketConnector();  
      
    //创建接受数据的过滤器  
    DefaultIoFilterChainBuilder chain = connector.getFilterChain();  
    chain.addLast("myChin", new ProtocolCodecFilter(  
        new ObjectSerializationCodecFactory()));  
    //设定这个过滤器将一行一行(/r/n)的读取数据  
    //chain.addLast("myText", new ProtocolCodecFilter(new TextLineCodecFactory()));  
      
    //服务器的消息处理器：一个SamplMinaServerHander对象  
    connector.setHandler(new ClientHander());  
      
    //set connect timeout  
    connector.setConnectTimeoutMillis(30);  
      
    //连接到服务器：  
    ConnectFuture cf = connector.connect(new InetSocketAddress("localhost",12121)); 
   //等待连接准备   
    cf.awaitUninterruptibly();
    
    IoSession session=cf.getSession();
    StringBuffer sb = new StringBuffer();
    for(int i=0;i<400;i++){
    	sb.append("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa").append(i);
    }
   
    System.out.println(sb.length());
    for(int i=0;i<1000;i++){
     session.write(sb.toString());
    // Thread.sleep(5);
     //buf.clear();
    }
    //Wait for the connection attempt to be finished.  
      
    //cf.getSession().getCloseFuture().awaitUninterruptibly();  //等待服务器关闭连接
      
    connector.dispose();
	}
}
