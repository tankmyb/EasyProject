package com.netty.pojo.server;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;

import com.netty.pojo.ReqBean;
import com.netty.pojo.RespBean;

public class ServerHandler extends SimpleChannelUpstreamHandler  
{  
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)  
            throws Exception  
    {  
        ReqBean p = (ReqBean) e.getMessage();  
        System.out.println("recive message,id:"+p.getId()+",reqMsg:"+p.getReqMsg());  
        RespBean respBean = new RespBean();
        respBean.setId(p.getId());
        respBean.setRespMsg("ret:"+p.getReqMsg());
        e.getChannel().write(respBean);
    }  
      
    public void exceptionCaught(  
            ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {  
        System.err.println("Client has a error,Error cause:"+e.getCause());  
        e.getChannel().close();  
    }  
}  
