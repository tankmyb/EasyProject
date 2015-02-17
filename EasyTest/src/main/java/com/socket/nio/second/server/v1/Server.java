package com.socket.nio.second.server.v1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

import com.socket.nio.second.CharsetUtils;

public class Server {
    private int index = 0;   
	  static int BLOCK = 100;
	  // 处理与客户端的交互
	  protected Selector selector;
	  protected ByteBuffer receivebuffer = ByteBuffer.allocate(BLOCK);
	  protected ByteBuffer sendbuffer = ByteBuffer.allocate(BLOCK);
	  public Server(int port) throws IOException {
	    selector = this.getSelector(port);
	  }

	  // 获取Selector
	  protected Selector getSelector(int port) throws IOException {
	    ServerSocketChannel server = ServerSocketChannel.open();
	    Selector sel = Selector.open();
	    server.socket().bind(new InetSocketAddress(port));
	    server.configureBlocking(false);
	    server.register(sel, SelectionKey.OP_ACCEPT);
	    return sel;
	  }

	  // 监听端口
	  public void listen() {
	    try {
	      for (;;) {
	        selector.select();
	        Iterator<SelectionKey> iter = selector.selectedKeys()
	            .iterator();
	        while (iter.hasNext()) {
	          SelectionKey key = iter.next();
	          iter.remove();
	          handleKey(key);
	        }
	      }
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	  }

	// 处理请求   
	    private void handleKey(SelectionKey selectionKey) throws IOException {   
	        // 接受请求   
	        ServerSocketChannel server = null;   
	        SocketChannel client = null;   
	        String receiveText;   
	        
	        int count=0;   
	        // 测试此键的通道是否已准备好接受新的套接字连接。   
	        if (selectionKey.isAcceptable()) {   
	            // 返回为之创建此键的通道。   
	            server = (ServerSocketChannel) selectionKey.channel();   
	            // 接受到此通道套接字的连接。   
	            // 此方法返回的套接字通道（如果有）将处于阻塞模式。   
	            client = server.accept();   
	            // 配置为非阻塞   
	            client.configureBlocking(false);   
	            // 注册到selector，等待连接   
	            //client.register(selector, SelectionKey.OP_READ);   
	        } else if (selectionKey.isReadable()) {   
	            // 返回为之创建此键的通道。   
	            client = (SocketChannel) selectionKey.channel();   
	            
	             
	            //读取服务器发送来的数据到缓冲区中   
	            count = client.read(receivebuffer);    
	            if (count > 0) {   
	                receiveText = new String( receivebuffer.array(),0,count);   
	                System.out.println("服务器端接受客户端数据--:"+receiveText);   
	                client.register(selector, SelectionKey.OP_WRITE);  
	              //将缓冲区清空以备下次写入   
		            sendbuffer.clear(); 
	            }   
	        } else if (selectionKey.isWritable()) {  
	        	if(index%2==0){
	        		try {
								Thread.sleep(2000L);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
	        	}
	            // 返回为之创建此键的通道。   
	            client = (SocketChannel) selectionKey.channel();     
	            //向缓冲区中输入数据   
	            sendbuffer.put(receivebuffer.array());   //直接使用receivebuffer是有问题的
	             //将缓冲区各标志复位,因为向里面put了数据标志被改变要想从中读取数据发向服务器,就要复位   
	            sendbuffer.flip();   
	            //输出到通道   
	            client.write(sendbuffer); 
	            receivebuffer.flip();
	            System.out.println("服务器端向客户端发送数据--："+CharsetUtils.UTF8.decode(receivebuffer).toString());   
	            client.register(selector, SelectionKey.OP_READ); 
	          //将缓冲区清空以备下次读取   
	            receivebuffer.clear(); 
	            index++;
	        }   
	    }   

	  public static void main(String[] args) {
	    int port = 12345;
	    try {
	      Server server = new Server(port);
	      System.out.println("Listernint on " + port);
	      while (true) {
	        server.listen();
	      }
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	  }

	
}
