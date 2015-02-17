package com.netty.pojo.client;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;

import com.netty.pojo.RespBean;
  
/** 
 * client和server接收消息共用的handler 
 * 由于两个都是继承自SimpleChannelUpstreamHandler,所以就写在一起了。 
 * @author Ransom 
 * 
 */  
public class ClientHandler extends SimpleChannelUpstreamHandler  
{  
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)  
            throws Exception  
    {  
        RespBean p = (RespBean) e.getMessage();  
        System.out.println("recive message,id:"+p.getId()+",respBean:"+p.getRespMsg());  
          
    }  
      
    public void exceptionCaught(  
            ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {  
        System.err.println("Client has a error,Error cause:"+e.getCause());  
        e.getChannel().close();  
    }  
}  