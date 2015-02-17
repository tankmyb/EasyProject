package com.mina.fileUpload.v3;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.handler.stream.StreamIoHandler;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

public class Server {
	public static void main(String args[]) throws IOException {  
	  
    NioSocketAcceptor acceptor = new NioSocketAcceptor();  
//    DefaultIoFilterChainBuilder chain = acceptor.getFilterChain();  
//    chain.addLast("codec", new ProtocolCodecFilter(  
//            new TextLineCodecFactory()));  

    StreamIoHandler handler = new StreamIoHandler() {  

        private ExecutorService pool = Executors.newCachedThreadPool();  

//        public void exceptionCaught(IoSession session, Throwable cause) {  
//            super.exceptionCaught(session, cause);  
//            System.out.println(cause);  
//       }  
//
//        public void sessionOpened(IoSession session) {  
//            super.sessionOpened(session);  
//            System.out.println(" session open....");  
//        }  


        @Override  
        protected void processStreamIo(IoSession session, InputStream in, OutputStream out) {  
            System.out.println("process stream...");  
            pool.execute(new Work(in, out));  
        }  
    };  
    acceptor.setHandler(handler);  
    acceptor.bind(new InetSocketAddress(8889));  
}  
}  

class Work extends Thread {  

private BufferedInputStream in;  
private BufferedOutputStream out;  

public Work(InputStream in, OutputStream out) {  
    this.in = new BufferedInputStream(in);  
    this.out = new BufferedOutputStream(out);  
}  

public void run() {  
    try {  
        FileOutputStream fos = new FileOutputStream(new File("d:/"+UUID.randomUUID().toString()+".zip"));  
        byte[] buf = new byte[2048];  
        int offset = 0;  
        while ((offset = in.read(buf)) != -1) {  
           // System.out.println(offset);  
            fos.write(buf, 0, offset);  
            fos.flush();  
        }             
        fos.close();  
        System.out.println("session over..");  
    } catch (IOException ex) {  
        ex.printStackTrace();
    }finally{
    	try {
			//out.close();
			//in.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }

}  

}
