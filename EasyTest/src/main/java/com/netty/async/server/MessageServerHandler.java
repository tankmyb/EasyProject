package com.netty.async.server;

import java.util.Date;

import org.jboss.netty.channel.ChannelHandler.Sharable;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;

import com.netty.async.ReqBean;
import com.netty.async.RespBean;

@Sharable
public class MessageServerHandler extends SimpleChannelUpstreamHandler {
 
 
    @Override
    public void messageReceived(
            ChannelHandlerContext ctx, MessageEvent e) throws InterruptedException {
        //System.out.println(this.getClass().hashCode());
        //System.out.println(e.getChannel());
        Thread.sleep(2000L);
        ReqBean reqBean = (ReqBean) e.getMessage();
        System.err.println(new Date()+"===got msg:"+reqBean.getReqMsg()+"==="+Thread.currentThread().getName());
        RespBean respBean = new RespBean();
        respBean.setId(reqBean.getId());
        respBean.setRespMsg("hello:"+reqBean.getReqMsg());
        e.getChannel().write(respBean);//(2)
    }
 
    @Override
    public void exceptionCaught(
            ChannelHandlerContext ctx, ExceptionEvent e) {
        e.getChannel().close();
    }
}
