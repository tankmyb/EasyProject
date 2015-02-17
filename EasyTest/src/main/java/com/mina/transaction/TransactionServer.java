package com.mina.transaction;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

public class TransactionServer {

	public static void main(String[] args) {


		final IoAcceptor acceptor = new NioSocketAcceptor();
		acceptor.setHandler(new TransactionServerHandler());
		acceptor.getFilterChain().addLast("myObj", new ProtocolCodecFilter(  
			        new ObjectSerializationCodecFactory()));  
		acceptor.getFilterChain().addLast("logging", new LoggingFilter());
		try{
			acceptor.bind(new InetSocketAddress(12121));
			System.out.println("12121 is started");
		}catch (Exception e) {
			e.printStackTrace();
		}
	
	
	}
}
