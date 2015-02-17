package com.netty.sync;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;
  
public class NettyClient {  
    private  int port=8080;  
    private String host="127.0.0.1";  
    private ClientBootstrap bootstrap;  
    private ClientHandler handler;  
    private ChannelFuture channelFuture;  
/** 
 * 初始化客户端 
 */  
    public void init() {  
    	handler = new ClientHandler();
        bootstrap = new ClientBootstrap(new NioClientSocketChannelFactory(  
                Executors.newCachedThreadPool(),  
                Executors.newCachedThreadPool()));  
          
        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {  
            public ChannelPipeline getPipeline() throws Exception {  
                ChannelPipeline channelPipeline = Channels.pipeline();  
                channelPipeline.addLast("encode", new StringEncoder());  
                channelPipeline.addLast("decode", new StringDecoder());  
                channelPipeline.addLast("handler", handler);  
                return channelPipeline;  
            }  
        });  
        bootstrap.setOption("connectTimeoutMillis", "5000");
        bootstrap.setOption("tcpNoDelay", true);  
        bootstrap.setOption("keepAlive", true);  
        bootstrap.setOption("reuseAddress", true);  
    }  
  
    public void start() {  
        channelFuture = bootstrap.connect(new InetSocketAddress(host,port));  
        System.out.println("连接远程服务器"+host+":"+port+"端口成功，你现在可以开始发消息了。");  
    }  
  
  
    public void stop() {  
        channelFuture.awaitUninterruptibly();  
        if (!channelFuture.isSuccess()) {  
            channelFuture.getCause().printStackTrace();  
        }  
        //等待或者监听数据全部完成  
        channelFuture.getChannel().getCloseFuture().awaitUninterruptibly();  
        //释放连接资源  
        bootstrap.releaseExternalResources();  
           
    }  
  
  
    public void setBootstrap(ClientBootstrap bootstrap) {  
        this.bootstrap = bootstrap;  
    }  
  
    public void setHandler(ClientHandler handler) {  
        this.handler = handler;  
    }  
  
  
    public void setPort(int port) {  
        this.port = port;  
    }  
  
    public ClientHandler getHandler() {  
        return handler;  
    }  
  
    public void setHost(String host) {  
        this.host = host;  
    }  
  
    public ChannelFuture getChannelFuture() {  
        return channelFuture;  
    }  
  public static void main(String[] args) throws InterruptedException {
	  NettyClient client = new NettyClient();
	  client.init();
	  client.start();
	  Thread.sleep(2000L);
	  for(int i=0;i<10;i++){
		  
	  }
}
}  