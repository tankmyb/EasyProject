package com.netty.async.client;
import java.util.Date;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

import com.netty.async.RespBean;
import com.netty.async.ResponseFuture;
  
public class ClientHandler extends SimpleChannelHandler {  
  
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)  
            throws Exception {  
        RespBean respBean = (RespBean) e.getMessage();  
        //System.out.println(""+ new Date().toString() + "," + respBean.getRespMsg());
        ResponseFuture.received(respBean);
    }  
}  