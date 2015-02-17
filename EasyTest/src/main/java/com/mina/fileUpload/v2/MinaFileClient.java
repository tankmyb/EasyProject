package com.mina.fileUpload.v2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.handler.stream.StreamIoHandler;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

public class MinaFileClient extends StreamIoHandler{  
    IoSession session;  
    public void setSession(IoSession session) {  
        this.session = session;  
    }  
    public IoSession getSession() {  
        return session;    
    }  
    @Override  
    protected void processStreamIo(IoSession session, InputStream in,  
            OutputStream out) {  
    	System.out.println(session.getId());
    	
        //客户端发送文件  
            File sendFile = new File("d:\\spring-flex-testdrive-spring3.zip");  
            FileInputStream fis = null;  
            try {  
                fis = new FileInputStream(sendFile);  
                  
            } catch (FileNotFoundException e) {  
                e.printStackTrace();  
            }  
            //放入线程让其执行  
             //客户端一般都用一个线程实现即可 不用线程池  
            new IoStreamThreadWork(fis,out).start();  
            return;  
    }  
      
    public void createClienStream(){  
    	Long start = System.currentTimeMillis();
    	
        int port = 8888;  
        String local = "127.0.0.1";  
          
        NioSocketConnector connector = new NioSocketConnector();  
        DefaultIoFilterChainBuilder chain = connector.getFilterChain();  
        ObjectSerializationCodecFactory factory = new ObjectSerializationCodecFactory();  
        factory.setDecoderMaxObjectSize(Integer.MAX_VALUE);  
        factory.setEncoderMaxObjectSize(Integer.MAX_VALUE);  
        chain.addLast("logging", new LoggingFilter());//用于打印日志可以不写  
        connector.setHandler(new MinaFileClient());  
        ConnectFuture connectFuture = connector.connect(new InetSocketAddress(local,port));  
        connectFuture.awaitUninterruptibly();//写上这句为了得到下面的session 意思是等待连接创建完成 让创建连接由异步变同步  
        //后来表明我开始的想法不行 动态依旧不能做到  
//      @SuppressWarnings("unused")  
//      IoSession session = connectFuture.getSession();  
//      setSession(session);  
        Long end = System.currentTimeMillis();
        System.out.println(end-start);
    }  
    public static void main(String[] args) {  
        MinaFileClient client = new MinaFileClient();  
        client.createClienStream();  
    }  

}
