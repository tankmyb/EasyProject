package com.netty.async.server;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

public class MessageServer {
	 
    public static void main(String[] args) throws Exception {
        // Configure the server.
        ServerBootstrap bootstrap = new ServerBootstrap(
                new NioServerSocketChannelFactory(
                        Executors.newCachedThreadPool(),
                        Executors.newCachedThreadPool()));
 
        // Set up the default event pipeline.
        bootstrap.setPipelineFactory(new MessageServerPipelineFactory());
 
        // Bind and start to accept incoming connections.
        bootstrap.bind(new InetSocketAddress(8089));
 
    }
}