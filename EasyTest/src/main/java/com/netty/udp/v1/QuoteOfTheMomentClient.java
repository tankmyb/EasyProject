package com.netty.udp.v1;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ConnectionlessBootstrap;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.FixedReceiveBufferSizePredictorFactory;
import org.jboss.netty.channel.socket.DatagramChannelFactory;
import org.jboss.netty.channel.socket.nio.NioDatagramChannelFactory;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;
import org.jboss.netty.util.CharsetUtil;

public class QuoteOfTheMomentClient {  
  
    private final int port;  
  
    public QuoteOfTheMomentClient(int port) {  
        this.port = port;  
    }  
  
    public void run() {  
        //UDP用的是DatagramChannelFactory  
        DatagramChannelFactory f =  
            new NioDatagramChannelFactory(Executors.newCachedThreadPool());  
      //UDP用的是ConnectionlessBootstrap  
        ConnectionlessBootstrap b = new ConnectionlessBootstrap(f);  
  
        // Configure the pipeline factory.  
        b.setPipelineFactory(new ChannelPipelineFactory() {  
            public ChannelPipeline getPipeline() throws Exception {  
                return Channels.pipeline(  
                        new StringEncoder(CharsetUtil.ISO_8859_1),  
                        new StringDecoder(CharsetUtil.ISO_8859_1),  
                        new QuoteOfTheMomentClientHandler());  
            }  
        });  
  
        // Enable broadcast 设置UDP广播，发送给同一网段的所有主机  
        b.setOption("broadcast", "true");  
  
        //UDP传输对字节有限制，设置传输字节大小  
        // Allow packets as large as up to 1024 bytes (default is 768).  
        // You could increase or decrease this value to avoid truncated packets  
        // or to improve memory footprint respectively.  
        //  
        // Please also note that a large UDP packet might be truncated or  
        // dropped by your router no matter how you configured this option.  
        // In UDP, a packet is truncated or dropped if it is larger than a  
        // certain size, depending on router configuration.  IPv4 routers  
        // truncate and IPv6 routers drop a large packet.  That's why it is  
        // safe to send small packets in UDP.  
        b.setOption(  
                "receiveBufferSizePredictorFactory",  
                new FixedReceiveBufferSizePredictorFactory(1024));  
        //DatagramChannel  
        ChannelFuture future =  b.connect(new InetSocketAddress("127.0.0.1", 3333));  
        try {
			Thread.sleep(1000L);
		} catch (InterruptedException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
        // Broadcast the QOTM request to port . 广播地址  
        future.getChannel().write("I want a QOTM");  
  
        // QuoteOfTheMomentClientHandler will close the DatagramChannel when a  
        // response is received.  If the channel is not closed within 5 seconds,  
        // print an error message and quit.  
        if (!future.getChannel().getCloseFuture().awaitUninterruptibly(5000)) {  
            System.err.println("QOTM request timed out.");  
            future.getChannel().close().awaitUninterruptibly();  
        }  
  
        f.releaseExternalResources();  
    }  
  
    public static void main(String[] args) throws Exception {  
//        int port;  
//        if (args.length > 0) {  
//            port = Integer.parseInt(args[0]);  
//        } else {  
//            port = 8080;  
//        }  
        int port = 3333;  
        new QuoteOfTheMomentClient(port).run();  
    }  
}  