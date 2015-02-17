package com.netty.protobuf.mulit;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.protobuf.ProtobufDecoder;
import org.jboss.netty.handler.codec.protobuf.ProtobufEncoder;

import com.netty.protobuf.CarInfos.Car.CarInfo;

/** 
 * desc:Htty服务端
 */
public class ProtobufNettyServer {
    
    public static void main(String[] args) {
        ServerBootstrap serverBootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(
                Executors.newSingleThreadExecutor(), Executors.newSingleThreadExecutor()));
        serverBootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            
            public ChannelPipeline getPipeline() throws Exception {
                ChannelPipeline p = Channels.pipeline();
              //解码  
                p.addLast("frameDecoder", new ProtobufVarint32FrameDecoder());  
         //构造函数传递要解码成的类型  
                p.addLast("protobufDecoder", new GameMessageDecoder());  
         //编码  
                p.addLast("frameEncoder", new GameMessageEncoder());  
                //p.addLast("protobufEncoder", new ProtobufEncoder());  
                p.addLast("handler", new ProtobufServerHandler());  //服务handler
                return p;
            }
        });
        serverBootstrap.bind(new InetSocketAddress(8080));
    }
}
/** 
 * desc:服务handler
 */
class ProtobufServerHandler extends SimpleChannelHandler{
    @Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
			throws Exception {
    	System.out.println(e.getMessage()+"==============");
    	GameMessage m = (GameMessage)e.getMessage();
    	CarInfo car = (CarInfo)m.getMessage();
    	System.out.println(car.getCarNumber());
		super.messageReceived(ctx, e);
	}

	/* 
     * desc:
     * (non-Javadoc)
     * @see org.jboss.netty.channel.SimpleChannelHandler#channelConnected(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.ChannelStateEvent)
     */
    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
       
    }
    
    /* 
     * desc:
     * (non-Javadoc)
     * @see org.jboss.netty.channel.SimpleChannelHandler#exceptionCaught(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.ExceptionEvent)
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
//        super.exceptionCaught(ctx, e);
        System.err.println("Unexpected exception from downstream." + e.getCause());
        e.getChannel().close();
    }
    
}