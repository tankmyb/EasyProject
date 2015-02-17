package com.netty.protobuf;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.protobuf.ProtobufEncoder;
import org.jboss.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;

import com.netty.protobuf.CarInfos.Car.CarInfo;
import com.netty.protobuf.CarInfos.Car.CarModel;
import com.netty.protobuf.CarInfos.Car.CarOwner;
import com.netty.protobuf.CarInfos.Car.Sex;

/** 
 * desc:Htty服务端
 */
public class ProtobufNettyServer {
    
    public static void main(String[] args) {
        ServerBootstrap serverBootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(
                Executors.newSingleThreadExecutor(), Executors.newSingleThreadExecutor()));
        serverBootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            
            public ChannelPipeline getPipeline() throws Exception {
                ChannelPipeline pipeline = Channels.pipeline();
                pipeline.addLast("protobufDecoder", new ProtobufVarint32FrameDecoder());  //netty自带的解码器
                pipeline.addLast("protobufEncoder", new ProtobufEncoder());  //netty自带的编码器
                pipeline.addLast("handler", new ProtobufServerHandler());  //服务handler
                return pipeline;
            }
        });
        serverBootstrap.bind(new InetSocketAddress("0.0.0.0",8080));
    }
}
/** 
 * desc:服务handler
 */
class ProtobufServerHandler extends SimpleChannelHandler{
    /* 
     * desc:
     * (non-Javadoc)
     * @see org.jboss.netty.channel.SimpleChannelHandler#channelConnected(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.ChannelStateEvent)
     */
    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        CarInfo o  = carInfosTrans();
        System.out.println("输出~~"+ o.getClass());
        ChannelFuture future = e.getChannel().write(o);
        future.addListener(ChannelFutureListener.CLOSE);
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
    
    private CarInfo carInfosTrans(){
        CarOwner.Builder owner = CarOwner.newBuilder();
        owner.setAge(18);
        owner.setHeight(170);
        owner.setName("jimmy");
        owner.setSex(Sex.FEMALE);
        CarInfo.Builder carInfo = CarInfo.newBuilder();
        carInfo.setBrand("大众");
        carInfo.setCarNumber("粤A 88888");
        carInfo.setColor("red");
        carInfo.setModel(CarModel.VOLKSWAGEN);
        carInfo.setPrice(1000);
        carInfo.setOwner(owner);
        
        CarInfo carInfoReq = carInfo.build();
//        long size = carInfoReq.getSerializedSize();
//        byte[] buf = carInfoReq.toByteArray();
        return carInfoReq;
    }
}