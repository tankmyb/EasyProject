package com.mina.fileUpload.v2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.handler.stream.StreamIoHandler;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

public class MinaFileServer extends StreamIoHandler{  
    public static final int PORT = 8888;  
    @Override  
    public void sessionOpened(IoSession session) {  
        System.out.println("客户端连接了:"+session.getRemoteAddress());  
        session.setAttribute("name", "name"+session.getId());
        super.sessionOpened(session);  
    }  
  
    protected void processStreamIo(IoSession session, InputStream in,OutputStream out) {  
        //设定一个线程池  
        //参数说明：最少数量3，最大数量6 空闲时间 3秒  
    	System.out.println(session.getAttribute("name"));
    	String name = (String)session.getAttribute("name");
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(3, 6, 30,TimeUnit.SECONDS,   
                //缓冲队列为3  
                new ArrayBlockingQueue<Runnable>(30),  
                //抛弃旧的任务  
                new ThreadPoolExecutor.DiscardOldestPolicy());  
        FileOutputStream fos = null;  
        File receiveFile = new File("d:\\"+UUID.randomUUID().toString()+".zip");  
        try {  
            fos = new FileOutputStream(receiveFile);  
        } catch (FileNotFoundException e1) {  
            e1.printStackTrace();  
        }  
        //将线程放入线程池 当连接很多时候可以通过线程池处理  
        threadPool.execute(new IoStreamThreadWork(in,fos));  
        //直接启动线程 连接很少可以选用下面  
      //new IoStreamThreadWork(in,fos).start();  
    }  
      
    public void createServerStream(){  
        //建立一个无阻塞服务端socket 用nio  
        NioSocketAcceptor acceptor = new NioSocketAcceptor();  
        //创建接收过滤器 也就是你要传送对象的类型  
        DefaultIoFilterChainBuilder chain = acceptor.getFilterChain();  
        //===========过滤器创建好了就开始设定============  
          
        //设定 对象传输工厂  
        //ObjectSerializationCodecFactory factory = new ObjectSerializationCodecFactory();  
        //设定传输最大值  
        //factory.setDecoderMaxObjectSize(Integer.MAX_VALUE);// 设定后服务器可以接收大数据  
        //factory.setEncoderMaxObjectSize(Integer.MAX_VALUE);  
        chain.addLast("logging", new LoggingFilter());//这个用于打印日志 可以不写  
        //设定服务端消息处理器  
        acceptor.setHandler(new MinaFileServer());  
        InetSocketAddress inetSocketAddress = null;  
        try {  
            inetSocketAddress = new InetSocketAddress(8888);  
            acceptor.bind(inetSocketAddress);  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
        System.out.println("文件服务器已经开启："+8888);  
    }  
    public static void main(String[] args) {  
        MinaFileServer server = new MinaFileServer();  
        server.createServerStream();  
    }  

}
