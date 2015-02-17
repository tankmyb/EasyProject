package com.netty4.http.FullHttpRequest;
import io.netty.channel.ChannelHandlerContext;  
import io.netty.channel.SimpleChannelInboundHandler;  
import io.netty.handler.codec.http.DefaultFullHttpRequest;  
import io.netty.handler.codec.http.DefaultFullHttpResponse;  
import io.netty.handler.codec.http.HttpResponseStatus;  
import io.netty.handler.codec.http.HttpVersion;  
  
public class HttpProtobufServerHandler extends SimpleChannelInboundHandler<Object>{  
    @Override  
    protected void channelRead0(ChannelHandlerContext ctx,  Object msg) throws Exception {
    	System.out.println("==========================");
        DefaultFullHttpRequest request=(DefaultFullHttpRequest)msg;  
        System.out.println(request);
        DefaultFullHttpResponse response=new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.ACCEPTED, request.content());  
        ctx.writeAndFlush(response);  
    }  
  
      
}  