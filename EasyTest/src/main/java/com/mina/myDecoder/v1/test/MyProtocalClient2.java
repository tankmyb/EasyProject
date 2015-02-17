package com.mina.myDecoder.v1.test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.future.IoFutureListener;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.mina.myDecoder.v1.MyProtocalCodecFactory;
import com.mina.myDecoder.v1.MyProtocalDecoder;
import com.mina.myDecoder.v1.MyProtocalEncoder;
import com.mina.myDecoder.v1.MyProtocalPack;

public class MyProtocalClient2 {
	private static final String HOST = "127.0.0.1";  
    private static final int PORT = 2500;  
    static long counter = 0;  
    final static int FC1 = 100;  
    static long start = 0;  
    /** 
     * 使用Mina的框架结构进行测试 
     *  
     * @param args 
     */  
    public static void main(String[] args) throws IOException {  
        start = System.currentTimeMillis();  
        IoConnector connector = new NioSocketConnector();  
        connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new MyProtocalCodecFactory(
        		new MyProtocalDecoder(Charset.forName("utf-8")),new MyProtocalEncoder(Charset.forName("utf-8")))));  
        connector.setHandler(new TimeClientHandler2());  
        connector.getSessionConfig().setReadBufferSize(100);  
        connector.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);   
        ConnectFuture connFuture = connector.connect(new InetSocketAddress(HOST, PORT));  
        connFuture.addListener(new IoFutureListener<ConnectFuture>() {  
            public void operationComplete(ConnectFuture future) {  
                try {  
                    if (future.isConnected()) {  
                        IoSession session = future.getSession(); 
                        StringBuffer sb = new StringBuffer();
                        for(int j=0;j<10;j++){
        	            	sb.append("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa===").append(j);
        	            }
                        sendData(session,sb.toString());    
                    } else {  
                        System.out.println("连接不存在 ");  
                    }  
                } catch (Exception e) {  
                    e.printStackTrace();  
                }  
            }  
        });  
        System.out.println("start client ...");  
    }  
    public static void sendData(IoSession session,String content) throws IOException {  
    	
        for (int i = 0; i < FC1; i++) {    
            MyProtocalPack pack = new MyProtocalPack((byte) i, content);  
            session.write(pack);  
            System.out.println("send data:" + pack);  
            try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }  
    }  
}  
class TimeClientHandler2 extends IoHandlerAdapter {  
    @Override  
    public void sessionOpened(IoSession session) {  
        // Set reader idle time to 10 seconds.  
        // sessionIdle(...) method will be invoked when no data is read  
        // for 10 seconds.  
        session.getConfig().setIdleTime(IdleStatus.READER_IDLE, 60);  
    }  
    @Override  
    public void sessionClosed(IoSession session) {  
        // Print out total number of bytes read from the remote peer.  
        System.err.println("Total " + session.getReadBytes() + " byte(s)");  
    }  
    @Override  
    public void sessionIdle(IoSession session, IdleStatus status) {  
        // Close the connection if reader is idle.  
        if (status == IdleStatus.READER_IDLE) {  
            session.close(true);  
        }  
    }  
    @Override  
    public void messageReceived(IoSession session, Object message) {  
        MyProtocalPack pack = (MyProtocalPack) message;  
        System.out.println("rec:" + pack);  
    }  
}
