package com.netty.protobuf.v1;


import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.protobuf.ProtobufDecoder;
import org.jboss.netty.handler.codec.protobuf.ProtobufEncoder;
import org.jboss.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import org.jboss.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

import com.netty.protobuf.CarInfos;
import com.netty.protobuf.CarInfos.Car.CarInfo;
import com.netty.protobuf.CarInfos.Car.CarModel;
import com.netty.protobuf.CarInfos.Car.CarOwner;
import com.netty.protobuf.CarInfos.Car.Sex;

/** 
 * desc:Htty服务端
 */
public class ProtobufNettyClient {
    
    public static void main(String[] args) {
    	ClientBootstrap bootstrap = new ClientBootstrap(
                new NioClientSocketChannelFactory(
                        Executors.newCachedThreadPool(),
                        Executors.newCachedThreadPool()));
    	bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            
            public ChannelPipeline getPipeline() throws Exception {
                ChannelPipeline p = Channels.pipeline();
                //解码用  
                p.addLast("frameDecoder", new ProtobufVarint32FrameDecoder());  
                //构造函数传递要解码成的类型  
                p.addLast("protobufDecoder", new ProtobufDecoder(CarInfo.getDefaultInstance()));  
         //编码用  
                p.addLast("frameEncoder", new ProtobufVarint32LengthFieldPrepender());  
                p.addLast("protobufEncoder", new ProtobufEncoder());  
                p.addLast("handler", new ProtobufClientHandler());  //服务handler
                return p;
            }
        });
    	bootstrap.connect(new InetSocketAddress("127.0.0.1", 8080));
    }
}
/** 
 * desc:服务handler
 */
class ProtobufClientHandler extends SimpleChannelHandler{
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
        //future.addListener(ChannelFutureListener.CLOSE);
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