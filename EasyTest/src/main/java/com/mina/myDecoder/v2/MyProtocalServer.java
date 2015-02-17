package com.mina.myDecoder.v2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

public class MyProtocalServer {
	public static Long start = System.currentTimeMillis();
	private static final int PORT = 2500;  
    public static void main(String[] args) throws IOException {  
        IoAcceptor acceptor = new NioSocketAcceptor();  
      
        acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new MyProtocalCodecFactory(Charset.forName("utf8"))));  
        acceptor.getSessionConfig().setReadBufferSize(10);  
        acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);  
        acceptor.setHandler(new MyHandler());  
        acceptor.bind(new InetSocketAddress(PORT));  
        System.out.println("start server ...");
        //start = System.currentTimeMillis();
    }
   static class MyHandler extends IoHandlerAdapter {  
        @Override  
        public void exceptionCaught(IoSession session, Throwable cause) throws Exception {  
            cause.printStackTrace();  
            System.out.println(System.currentTimeMillis()-start);
        }  
        @Override  
        public void messageReceived(IoSession session, Object message) throws Exception {  
            MyProtocalPack pack=(MyProtocalPack)message;  
            System.out.println("Rec:" + pack);  
        }  
        @Override  
        public void sessionIdle(IoSession session, IdleStatus status) throws Exception {  
        	System.out.println("IDLE " + session.getIdleCount(status));  
        }  
    }
}  

