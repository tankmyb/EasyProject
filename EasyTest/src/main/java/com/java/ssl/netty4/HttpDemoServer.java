package com.java.ssl.netty4;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
 
/**
 * A HTTP server showing how to use the HTTP multipart package for file uploads.
 */
public class HttpDemoServer {
 
    private final int port;
    public static boolean isSSL;
 
    public HttpDemoServer(int port) {
        this.port = port;
    }
 
    public void run() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new HttpDemoServerInitializer());
 
            Channel ch = b.bind(port).sync().channel();
            ch.closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
 
    public static void main(String[] args) throws Exception {
        int port = 8888;
        isSSL = true;
        new HttpDemoServer(port).run();
    }
}