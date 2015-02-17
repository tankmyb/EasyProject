package com.netty.udp.v1;

import java.net.InetSocketAddress;

import org.jboss.netty.bootstrap.ConnectionlessBootstrap;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.FixedReceiveBufferSizePredictorFactory;
import org.jboss.netty.channel.socket.DatagramChannelFactory;
import org.jboss.netty.channel.socket.nio.NioDatagramChannelFactory;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;
import org.jboss.netty.util.CharsetUtil;

/** 
 * A UDP server that responds to the QOTM (quote of the moment) request to a 
 * {@link QuoteOfTheMomentClient}. 
 * 
 */  
public class QuoteOfTheMomentServer {  
  
    private final int port;  
  
    public QuoteOfTheMomentServer(int port) {  
        this.port = port;  
    }  
  
    public void run() {  
        DatagramChannelFactory f = new NioDatagramChannelFactory();  
        ConnectionlessBootstrap b = new ConnectionlessBootstrap(f);  
  
        // Configure the pipeline factory.  
        b.setPipelineFactory(new ChannelPipelineFactory() {  
            public ChannelPipeline getPipeline() throws Exception {  
                return Channels.pipeline(  
                        new StringEncoder(CharsetUtil.ISO_8859_1),  
                        new StringDecoder(CharsetUtil.ISO_8859_1),  
                        new QuoteOfTheMomentServerHandler());  
            }  
        });  
  
        // Enable broadcast  
        b.setOption("broadcast", "false");  
  
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
  
        // Bind to the port and start the service.  
        b.bind(new InetSocketAddress(port));  
    }  
  
    public static void main(String[] args) throws Exception {  
//        int port;  
//        if (args.length > 0) {  
//            port = Integer.parseInt(args[0]);  
//        } else {  
//            port = 8080;  
//        }  
        int port = 3333;  
        new QuoteOfTheMomentServer(port).run();  
    }  
}  