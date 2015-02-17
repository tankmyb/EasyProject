package com.netty.monitor;

import java.util.Date;

import org.jboss.netty.channel.ChannelHandler.Sharable;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.handler.execution.MemoryAwareThreadPoolExecutor;

@Sharable
public class MessageServerHandler extends SimpleChannelUpstreamHandler {
 
	private MemoryAwareThreadPoolExecutor executor;
	public MessageServerHandler(MemoryAwareThreadPoolExecutor executor){
		this.executor = executor;
	}
    @Override
    public void messageReceived(
            ChannelHandlerContext ctx, MessageEvent e) throws InterruptedException {
        if (!(e.getMessage() instanceof String)) {
            return;//(1)
        }
        String msg = (String) e.getMessage();
        //System.err.println(new Date()+"===got msg:"+msg+"==="+Thread.currentThread().getName());
        if(msg.endsWith("9")){
        	System.out.println(executor.getCompletedTaskCount()+"====getCompletedTaskCount");
        	System.out.println(executor.getActiveCount()+"===getActiveCount=");
        	System.out.println(executor.getTaskCount()+"===getTaskCount=");
        	System.out.println(executor.getQueue().size()+"===getQueue=");
        	System.out.println(msg+"=========="+new Date());
        	System.out.println();
        }
        Thread.sleep(10000l);
        e.getChannel().write("return:"+msg);//(2)
    }
 
    @Override
    public void exceptionCaught(
            ChannelHandlerContext ctx, ExceptionEvent e) {
        e.getChannel().close();
    }
}
