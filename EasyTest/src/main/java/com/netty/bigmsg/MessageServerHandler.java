package com.netty.bigmsg;

import java.util.Date;

import org.jboss.netty.channel.ChannelHandler.Sharable;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.handler.execution.MemoryAwareThreadPoolExecutor;

@Sharable
public class MessageServerHandler extends SimpleChannelUpstreamHandler {
    @Override
    public void messageReceived(
            ChannelHandlerContext ctx, final MessageEvent e) throws InterruptedException {
				if (!(e.getMessage() instanceof String)) {
		            return;//(1)
		        }
		        String msg = (String) e.getMessage();
		        System.err.println(new Date()+"===got msg:"+msg+"==="+Thread.currentThread().getName());
		        System.out.println();
		        System.out.println();
		       // e.getChannel().write("return:"+msg);//(2)
        
    }
 
    @Override
    public void exceptionCaught(
            ChannelHandlerContext ctx, ExceptionEvent e) {
        e.getChannel().close();
    }
}
