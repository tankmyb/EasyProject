package com.netty.udp.split.client;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;

public class ClientHandler extends SimpleChannelUpstreamHandler{

	 @Override  
	    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {  
	        System.out.println(e.getRemoteAddress() + " ->：" + e.getMessage());  
	          
//	      e.getChannel().write(message, remoteAddress)  
	    }

}
