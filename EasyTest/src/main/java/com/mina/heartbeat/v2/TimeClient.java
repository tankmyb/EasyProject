package com.mina.heartbeat.v2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

public class TimeClient {

	public static void main(String[] args) throws InterruptedException, IOException {
		 //Create TCP/IP connection  
    NioSocketConnector connector = new NioSocketConnector();  
      
    //创建接受数据的过滤器  
    DefaultIoFilterChainBuilder chain = connector.getFilterChain();  
    //设定这个过滤器将一行一行(/r/n)的读取数据  
    chain.addLast("myText", new ProtocolCodecFilter(new TextLineCodecFactory()));  
    chain.addLast("keep-alive", new HachiKeepAliveFilterInMina());//心跳 
    //服务器的消息处理器：一个SamplMinaServerHander对象  
    connector.setHandler(new ClientHander());  
      
    //set connect timeout  
    connector.setConnectTimeoutMillis(30);  
      
    //连接到服务器：  
    ConnectFuture cf = connector.connect(new InetSocketAddress("localhost",12121)); 
   //等待连接准备   
    cf.awaitUninterruptibly();
    
    IoSession session=cf.getSession();
    BufferedReader reader=new BufferedReader(new InputStreamReader(System.in));
    for(;;){
    	String str = reader.readLine();
    	if(str.equals("quit")){
    		break;
    	}
    	session.write(str);
    }

    connector.dispose();
	}
}
