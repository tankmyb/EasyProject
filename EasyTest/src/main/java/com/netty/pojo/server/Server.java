package com.netty.pojo.server;

import static org.jboss.netty.channel.Channels.pipeline;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.serialization.ClassResolvers;
import org.jboss.netty.handler.codec.serialization.ObjectDecoder;
import org.jboss.netty.handler.codec.serialization.ObjectEncoder;

import com.netty.pojo.ReqBean;
import com.netty.pojo.client.ClientHandler;
  
/** 
 * 在本地8080端口启动netty服务 
 * @author Ransom 
 * 
 */  
public class Server  
{  
    public static void main(String[] args)  
    {  
       /* 
        * server的注释和client类似，在这里就不重复了 
        * 但是需要注意的是server初始化的是ServerBootstrap的实例 
        * client初始化的是ClientBootstrap，两个是不一样的。 
        * 里面的channelfactory也是NioServerSocketChannelFactory。 
        */  
          
        ServerBootstrap bootstrap = new ServerBootstrap(  
                new NioServerSocketChannelFactory(  
                        Executors.newCachedThreadPool(),  
                        Executors.newCachedThreadPool()));  
  
        bootstrap.setPipelineFactory(new ChannelPipelineFactory()  
        {  
  
            @Override  
            public ChannelPipeline getPipeline() throws Exception  
            {  
                ChannelPipeline pipleline = pipeline();  
                pipleline.addLast("encode", new ObjectEncoder());  
                pipleline.addLast("decode", new ObjectDecoder(ClassResolvers.cacheDisabled(ReqBean.class.getClassLoader())));  
                pipleline.addLast("handler", new ServerHandler());  
                return pipleline;  
            }  
  
        });  
          
        bootstrap.bind(new InetSocketAddress(8080));  
        System.out.println("server start at 8080");  
  
    }  
}  