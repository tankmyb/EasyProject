package com.netty.reconn.server;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.util.HashedWheelTimer;
import org.jboss.netty.util.Timer;

public class ChatServer {
 public static void main(String[] args) {
  ChannelFactory factory = new NioServerSocketChannelFactory(Executors
    .newCachedThreadPool(), Executors.newCachedThreadPool(),
    Runtime.getRuntime().availableProcessors() + 1);

  Timer timer = new HashedWheelTimer();
  
  ServerBootstrap bootstrap = new ServerBootstrap(factory);
  bootstrap.setPipelineFactory(new ChatPipelineServerFactory(timer));
  bootstrap.setOption("child.tcpNoDelay", true);
  bootstrap.setOption("child.keepAlive", true);
  bootstrap.setOption("reuseAddress", true);

  bootstrap.bind(new InetSocketAddress(9001));
 }
}