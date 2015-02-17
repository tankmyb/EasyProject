package com.netty.udp.split.client;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ConnectionlessBootstrap;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.FixedReceiveBufferSizePredictorFactory;
import org.jboss.netty.channel.socket.DatagramChannel;
import org.jboss.netty.channel.socket.DatagramChannelFactory;
import org.jboss.netty.channel.socket.nio.NioDatagramChannelFactory;


public class Client {
	  
	  
    private final int port;  
  
    public Client(int port) {  
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
                        new UDPClientEncoder(),  
                        new UDPClientDecoder(),  
                        new ClientHandler());  
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
        DatagramChannel c = (DatagramChannel) b.bind(new InetSocketAddress(0));  
  
        // Broadcast the QOTM request to port . 广播地址
        StringBuffer sb = new StringBuffer();
        for(int i=0;i<30;i++){
        	sb.append(i).append(" ");
        }
        List<ChannelBuffer> list = str2Byte(sb.toString(),c);
        for(ChannelBuffer cb:list){
        	c.write(cb, new InetSocketAddress("127.0.0.1", port)); 
        }
         
  
        // QuoteOfTheMomentClientHandler will close the DatagramChannel when a  
        // response is received.  If the channel is not closed within 5 seconds,  
        // print an error message and quit.  
        if (!c.getCloseFuture().awaitUninterruptibly(5000)) {  
            System.err.println("QOTM request timed out.");  
            c.close().awaitUninterruptibly();  
        }  
  
        f.releaseExternalResources();  
    }  
    public List<ChannelBuffer> str2Byte(String str,Channel channel){
    	List<ChannelBuffer> list = new ArrayList<ChannelBuffer>();
    	int maxLen = 10;
      	byte[] b = str.getBytes();
      	Integer len =b.length;
      	if(len<=maxLen){
      		ChannelBuffer cb = ChannelBuffers.dynamicBuffer(maxLen, channel.getConfig().getBufferFactory());
			cb.writeInt(b.length);
			cb.writeBytes(b);
			list.add(cb);
      		return list;
      	}
      	int packNum = len/maxLen;
      	if(len%maxLen!=0){
      		packNum++;
      	}
      	int pos =0;
      	int pos1 =0;
      	for(int i=0;i<packNum;i++){
      		int length = maxLen;
      		if(len-pos<length){
      			length= len-pos;
      		}
      		//byte[] seq=(i+",").getBytes();
      		byte[] pack = new byte[length];
      		//System.out.println(pos);
      		
      		//System.arraycopy(seq, 0, pack, 0, seq.length);
      		System.arraycopy(b, pos, pack, 0, length);
      		ChannelBuffer cb = ChannelBuffers.dynamicBuffer(maxLen, channel.getConfig().getBufferFactory());
            cb.writeInt(0);
            cb.writeInt(i);
			cb.writeInt(pack.length);
			cb.writeBytes(pack);
      		list.add(cb);
      		System.out.println(new String(pack));
      		pos+=maxLen;
      		if(pos>len){
      			pos = len;
      		}
      	}
      	return list;
    }
    
    public static void main(String[] args) throws Exception {  

        int port = 9001;  
        new Client(port).run();  
    }  

}
