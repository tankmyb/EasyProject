package com.socket.nio.second.server.v3;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

import com.socket.nio.second.CharsetUtils;

public class SelectorServer {
	private static int DEFAULT_SERVERPORT = 6018;//默认端口
    private static int DEFAULT_BUFFERSIZE = 1024;//默认缓冲区大小为1024字节
    private ServerSocketChannel channel;
    private LinkedList<SocketChannel> clients;
    private Selector readSelector;
    private ByteBuffer receivebuffer;//字节缓冲区
    private ByteBuffer sendbuffer;//字节缓冲区
    private int port;
    
    public SelectorServer(int port) throws IOException
    {
        this.port = port;
        this.clients = new LinkedList<SocketChannel>();
        this.channel = null;
        this.readSelector = Selector.open();//打开选择器
        this.sendbuffer = ByteBuffer.allocate(DEFAULT_BUFFERSIZE);
        this.receivebuffer = ByteBuffer.allocate(DEFAULT_BUFFERSIZE);
    }
     // 服务器程序在服务循环中调用sericeClients()方法为已接受的客户服务
    public void serviceClients()throws IOException
    {
    	int count;
        Set keys;
        Iterator it;
        SelectionKey key;
        SocketChannel client;
        // 在readSelector上调用select()方法，参数1代表如果调用select的时候 那么阻塞最多1秒钟等待可用的客户端连接
        if(readSelector.select(1) > 0)
        {
            keys = readSelector.selectedKeys(); // 取得代表端通道的键集合
            it = keys.iterator();
           // 遍历，为每一个客户服务 
            while(it.hasNext()) 
            {
               key = (SelectionKey)it.next();
               if (key.isReadable()) {
       			// 返回为之创建此键的通道。
       			client = (SocketChannel) key.channel();
       			// 读取服务器发送来的数据到缓冲区中
       			count = client.read(receivebuffer);
       			if (count > 0) {
       				String receiveText = new String(receivebuffer.array(), 0, count);
       				System.out.println("服务器端接受客户端数据--:" + receiveText);
       				client.register(readSelector, SelectionKey.OP_WRITE);
       				// 将缓冲区清空以备下次写入
       				sendbuffer.clear();
       			}
       		} else if (key.isWritable()) {
       			// 返回为之创建此键的通道。
       			client = (SocketChannel) key.channel();
       			// 向缓冲区中输入数据
       			sendbuffer.put(receivebuffer.array()); // 直接使用receivebuffer是有问题的
       			// 将缓冲区各标志复位,因为向里面put了数据标志被改变要想从中读取数据发向服务器,就要复位
       			sendbuffer.flip();
       			// 输出到通道
       			client.write(sendbuffer);
       			receivebuffer.flip();
       			System.out.println("服务器端向客户端发送数据--："
       					+ CharsetUtils.UTF8.decode(receivebuffer).toString());
       			client.register(readSelector, SelectionKey.OP_READ);
       			// 将缓冲区清空以备下次读取
       			receivebuffer.clear();
       		}
               
            }
         }
    }
    
    public void registerClient(SocketChannel client) throws IOException
    {// 配置和注册代表客户连接的通道对象
        client.configureBlocking(false);  // 设置此通道使用非阻塞模式    
        client.register(readSelector, SelectionKey.OP_READ); // 将这个通道注册到选择器上
        clients.add(client); //保存这个通道对象
    }
    public void listen() throws IOException
    { //服务器开始监听端口，提供服务
        ServerSocket socket;
        SocketChannel client;
        channel = ServerSocketChannel.open(); // 打开通道
        socket = channel.socket();   //得到与通到相关的socket对象
        socket.bind(new InetSocketAddress(port), 10);   //将scoket榜定在制定的端口上
        //配置通到使用非阻塞模式，在非阻塞模式下，可以编写多道程序同时避免使用复杂的多线程
        channel.configureBlocking(false);    
        try 
        {
            while(true) 
            {//     与通常的程序不同，这里使用channel.accpet()接受客户端连接请求，而不是在socket对象上调用accept(),这里在调用accept()方法时如果通道配置为非阻塞模式,那么accept()方法立即返回null，并不阻塞
                client = channel.accept();    
               
                if(client != null)
                {
                    registerClient(client); // 注册客户信息
                }
                serviceClients();  // 为以连接的客户服务
            }
        } 
        finally 
        {
            socket.close(); // 关闭socket，关闭socket会同时关闭与此socket关联的通道
        }
    }
    public static void main(String[] args) throws IOException 
    {
        System.out.println("服务器启动");
        SelectorServer server = new SelectorServer(SelectorServer.DEFAULT_SERVERPORT);
        server.listen(); //服务器开始监听端口，提供服务

        
    }


}
