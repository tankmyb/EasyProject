package com.netty.message;

import java.util.Date;

import org.jboss.netty.channel.ChannelHandler.Sharable;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.handler.execution.MemoryAwareThreadPoolExecutor;

@Sharable
public class MessageServerHandler extends SimpleChannelUpstreamHandler {
   public static ThreadPoolManager threadPoolManager = ThreadPoolManager.getInstance();
    @Override
    public void messageReceived(
            ChannelHandlerContext ctx, final MessageEvent e) throws InterruptedException {
    	threadPoolManager.execute(new Runnable() {
			
			@Override
			public void run() {
				if (!(e.getMessage() instanceof String)) {
		            return;//(1)
		        }
		        String msg = (String) e.getMessage();
		        System.err.println(new Date()+"===got msg:"+msg+"==="+Thread.currentThread().getName());
		       /* if(msg.endsWith("9")){
		        	threadPoolManager.getTaskSize();
		        	System.out.println(msg+"=========="+new Date());
		        	System.out.println();
		        }
		        try{
		        	Thread.sleep(10000l);
		        }catch(Exception ex){
		        	ex.printStackTrace();
		        }
		        */
		       // e.getChannel().write("return:"+msg);//(2)
			}
		});
        
    }
 
    @Override
    public void exceptionCaught(
            ChannelHandlerContext ctx, ExceptionEvent e) {
        e.getChannel().close();
    }
}
