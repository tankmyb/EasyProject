package com.mina.chat;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

public class ChatServer {

	public static void main(String[] args) {

		final IoAcceptor acceptor = new NioSocketAcceptor();
		acceptor.setHandler(new ChatServerHandler());
		acceptor.getFilterChain().addLast("myText", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("utf8"))));
		acceptor.getFilterChain().addLast("logging", new LoggingFilter());
		try{
			acceptor.bind(new InetSocketAddress(12121));
			System.out.println("12121 is opened");
		}catch (Exception e) {
			e.printStackTrace();
		}
	
	}
}
