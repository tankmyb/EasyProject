package com.netty.helloworld;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;

public class RemoveHandler extends SimpleChannelUpstreamHandler  
{  
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)  
            throws Exception  
    {  
        System.out.println("remove========"+e.getMessage());  
        //ctx.getPipeline().remove("remove");
        //super.messageReceived(ctx, e);
    }  
      
    public void exceptionCaught(  
            ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {  
        System.err.println("Client has a error,Error cause:"+e.getCause());  
        e.getChannel().close();  
    }  

}
