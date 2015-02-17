package com.mina.transaction;

import java.net.InetSocketAddress;
import java.util.List;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

public class TransactionClient {

	private IoSession session;
	private String bid;
	private TransactionClientHandler handler;
	public TransactionClient(String bid){
		this.bid = bid;
		handler = new TransactionClientHandler(this,bid);
		getConnectFuture();
	    
	}
	public void getConnectFuture(){
		NioSocketConnector connector = new NioSocketConnector();  
	    //创建接受数据的过滤器  
	    DefaultIoFilterChainBuilder chain = connector.getFilterChain();   
	    //设定这个过滤器将一行一行(/r/n)的读取数据  
	    chain.addLast("myObj", new ProtocolCodecFilter(  
	            new ObjectSerializationCodecFactory()));  
	    //服务器的消息处理器：一个SamplMinaServerHander对象  
	    connector.setHandler(handler);      
	    //set connect timeout  
	    connector.setConnectTimeoutMillis(3000L);  
	      
	    //连接到服务器：  
	    ConnectFuture cf = connector.connect(new InetSocketAddress("localhost",12121)); 
	   //等待连接准备   
	    cf.awaitUninterruptibly();
	    
	    try {
	    	session=cf.getSession();
	    	System.out.println("success");
		} catch (Exception e) {
			e.printStackTrace();
	    	this.getConnectFuture();
		}
	   
	}
	public void send(List<String> list){
		String [] a = new String[3];
		a[4]="2323";
		session.write(list);
	}
	public static void main(String[] args) {
		TransactionClient client = new TransactionClient("order");
	}
}
