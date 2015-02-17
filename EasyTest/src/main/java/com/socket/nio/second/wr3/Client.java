package com.socket.nio.second.wr3;

import java.io.ByteArrayOutputStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

import com.socket.nio.second.CharsetUtils;

public class Client {
	public static final Integer size = 1024;
	public void start()
  {
      try
      {
          //定义一个记录套接字通道事件的对象
          Selector selector = Selector.open();
          //定义一个服务器地址的对象
          SocketAddress address = new InetSocketAddress("localhost",55555);
          //定义异步客户端
          SocketChannel client=SocketChannel.open(address);
          //将客户端设定为异步
          client.configureBlocking(false);
          //在轮讯对象中注册此客户端的读取事件(就是当服务器向此客户端发送数据的时候)
          client.register(selector, SelectionKey.OP_READ);
          //要发送的数据
          //String a="我人有仍佣兵的人搂秋搂翻腾粉料秋";
          String a="我qwertyuio佣兵的人搂pasdfghjklsddsssssssssssss腾粉料秋腾粉料秋腾粉料秋腾粉料秋腾粉料秋腾粉料秋腾粉料秋腾粉料秋腾粉料秋腾粉料秋腾粉料秋腾粉料秋腾粉料秋";
          System.out.println(a.getBytes("UTF8").length+"=====");
          //定义用来存储发送数据的byte缓冲区
          ByteBuffer sendbuffer=ByteBuffer.allocate(209);
          //定义用于接收服务器返回的数据的缓冲区
          ByteBuffer readBuffer=ByteBuffer.allocate(size);
          //将数据put进缓冲区
          sendbuffer.put(a.getBytes());
          //将缓冲区各标志复位,因为向里面put了数据标志被改变要想从中读取数据发向服务器,就要复位
          sendbuffer.flip();
          //向服务器发送数据
          client.write(sendbuffer);
          sendbuffer.flip();
          System.out.println("发送数据: "+CharsetUtils.UTF8.decode(sendbuffer).toString());
         
          //利用循环来读取服务器发回的数据
          while(true)
          {
              //如果客户端连接没有打开就退出循环
              if(!client.isOpen()) break;
              //此方法为查询是否有事件发生如果没有就阻塞,有的话返回事件数量
              int shijian=selector.select();
              //如果没有事件返回循环
              if(shijian==0)
              {
                  continue;
              }
              //定义一个临时的客户端socket对象
              SocketChannel sc;
              //遍例所有的事件
              for (SelectionKey key : selector.selectedKeys())
              {
                  //删除本次事件
                  selector.selectedKeys().remove(key);
                  //如果本事件的类型为read时,表示服务器向本客户端发送了数据
                  if (key.isReadable())
                  {
                      //将临时客户端对象实例为本事件的socket对象
                      sc=(SocketChannel) key.channel();
                      //定义一个用于存储所有服务器发送过来的数据
                      ByteArrayOutputStream bos=new ByteArrayOutputStream();
                      //将缓冲区清空以备下次读取
                      readBuffer.clear();
                      //此循环从本事件的客户端对象读取服务器发送来的数据到缓冲区中
                      int count = 0;
                      while((count=sc.read(readBuffer))!=-1)
                      {
                    	  readBuffer.flip();
                          //将本次读取的数据存到byte流中
                          bos.write(readBuffer.array(),0,count);   
                          //将缓冲区清空以备下次读取
                          readBuffer.clear();
                      }
                      //如果byte流中存有数据
                      if(bos.size()>0)
                      {
                          //建立一个普通字节数组存取缓冲区的数据
                          byte[] b=bos.toByteArray();
                         
                          System.out.println("接收数据: " + new String(b));
                          //关闭客户端连接,此时服务器在read读取客户端信息的时候会返回-1
                          client.close();
                          System.out.println("连接关闭!");
                      }
                  }
              }
          }
      }
      catch(Exception e)
      {
          e.printStackTrace();
      }
  }
	public static void main(String[] args) {
		new Client().start();
	}

}
