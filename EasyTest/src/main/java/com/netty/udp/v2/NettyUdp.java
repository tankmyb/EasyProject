package com.netty.udp.v2;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ConnectionlessBootstrap;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.channel.socket.DatagramChannel;
import org.jboss.netty.channel.socket.nio.NioDatagramChannelFactory;
import org.jboss.netty.handler.codec.frame.FixedLengthFrameDecoder;
  
public class NettyUdp {  
  
    private int size;  
    private ConnectionlessBootstrap b;  
    private DatagramChannel datagramChannel;  
  
    public NettyUdp(int port, int size) {  
        super();  
        this.size = size;  
          
        init(port);  
    }  
      
    private void init(int port){  
        b = new ConnectionlessBootstrap(new NioDatagramChannelFactory(Executors.newCachedThreadPool()));  
  
        b.setOption("tcpNoDelay", true);  
        b.setOption("receiveBufferSize", 1048576); // 1M  
           
        b.setPipelineFactory(new ChannelPipelineFactory() {  
            @Override  
            public ChannelPipeline getPipeline() throws Exception {  
                ChannelPipeline pipeline = Channels.pipeline();  
                pipeline.addLast("stick", new FixedLengthFrameDecoder(size));  
                pipeline.addLast("decoder", new UDPDecoder(size));  
                pipeline.addLast("encoder", new UDPEncoder(size));  
                pipeline.addLast("logic", new MyHandler());  
  
                return pipeline;  
            }  
        });  
          
        datagramChannel = (DatagramChannel) b.bind(new InetSocketAddress(port));  
        System.out.println(" Server is starting ……");  
    }  
      
    public void writeString(String message, String remoteHost, int remotePort) {  
        datagramChannel.write(message, new InetSocketAddress(remoteHost, remotePort));  
    }  
  
      
    public void shutdown(){  
        if(datagramChannel != null){  
            datagramChannel.close();  
        }  
        if(b != null){  
            b.releaseExternalResources();  
        }  
    }  
  
}  
  
class MyHandler extends SimpleChannelUpstreamHandler {  
  
    @Override  
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {  
        System.out.println(e.getRemoteAddress() + " ->：" + e.getMessage());  
          
//      e.getChannel().write(message, remoteAddress)  
    }  
  
}  