package com.socket.nio.second.wr3;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class Server {
//定义一个临时的socket
  SocketChannel sc;
  public void start()
  {
      try
      {
          //定义一个事件选择器对象记录套接字通道的事件
          Selector selector = Selector.open();
          //定义一个异步服务器socket对象
          ServerSocketChannel ssc=ServerSocketChannel.open();
          //将此socket对象设置为异步
          ssc.configureBlocking(false);
          //定义服务器socket对象-用来指定异步socket的监听端口等信息
          ServerSocket ss=ssc.socket();
          //定义存放监听端口的对象
          InetSocketAddress address = new InetSocketAddress(55555);
          //将服务器与这个端口绑定
          ss.bind(address);
          //将异步的服务器socket对象的接受客户端连接事件注册到selector对象内
          ssc.register(selector, SelectionKey.OP_ACCEPT);
         
          System.out.println("端口注册完毕!");
          //通过此循环来遍例事件
          while(true)
          {
              //查询事件如果一个事件都没有就阻塞
              selector.select();
              //定义一个byte缓冲区来存储收发的数据
              ByteBuffer echoBuffer=ByteBuffer.allocate(100);
             
              //此循环遍例所有产生的事件
              for (SelectionKey key : selector.selectedKeys())
              {
                  //如果产生的事件为接受客户端连接(当有客户端连接服务器的时候产生)
                  if((key.readyOps()&SelectionKey.OP_ACCEPT)==SelectionKey.OP_ACCEPT)
                  {
                      selector.selectedKeys().remove(key);
                      //定义一个服务器socket通道
                      ServerSocketChannel subssc=(ServerSocketChannel)key.channel();
                      //将临时socket对象实例化为接收到的客户端的socket
                      sc=subssc.accept();
                      //将客户端的socket设置为异步
                      sc.configureBlocking(false);
                      //将客户端的socket的读取事件注册到事件选择器中
                      sc.register(selector, SelectionKey.OP_READ);
                      //将本此事件从迭带器中删除
                      System.out.println("有新连接:"+sc);
                  }
                  //如果产生的事件为读取数据(当已连接的客户端向服务器发送数据的时候产生)
                  else if((key.readyOps()&SelectionKey.OP_READ)==SelectionKey.OP_READ)
                  {
                      //将本次事件删除
                      selector.selectedKeys().remove(key);
                      //临时socket对象实例化为产生本事件的socket
                      sc=(SocketChannel) key.channel();
                      //定义一个用于存储byte数据的流对象
                      ByteArrayOutputStream bos=new ByteArrayOutputStream();
                      //先将客户端的数据清空
                      echoBuffer.clear();
                      //a为读取到数据的长度
                      try
                      {
                      //循环读取所有客户端数据到byte缓冲区中,当有数据的时候read函数返回数据长度
                      //NIO会自动的将缓冲区一次容纳不下的自动分段
                          int readInt=0;
                          while((readInt=sc.read(echoBuffer)) > 0)
                          {
                              //如果获得数据长度比缓冲区大小小的话
                              if(readInt<echoBuffer.capacity())
                              {
                                  //建立一个临时byte数组,将齐长度设为获取的数据的长度
                                  byte[] readByte=new byte[readInt];
                                  //循环向此临时数组中添加数据
                                  for(int i=0;i<readInt;i++)
                                  {
                                      readByte[i]=echoBuffer.get(i);
                                  }
                                  //将此数据存入byte流中
                                  bos.write(readByte);
                              }
                              //否则就是获得数据长度等于缓冲区大小
                              else
                              {
                                  //将读取到的数据写入到byte流对象中
                                     bos.write(echoBuffer.array());
                              }
                                 //将缓冲区清空，以便进行下一次存储数据
                                 echoBuffer.clear();
                          }
                          //当循环结束时byte流中已经存储了客户端发送的所有byte数据
                          System.out.println("接收数据: "+new String(bos.toByteArray()));
                      }
                      catch(Exception e)
                      {
                          //当客户端在读取数据操作执行之前断开连接会产生异常信息
                          e.printStackTrace();
                          //将本socket的事件在选择器中删除
                          key.cancel();
                          break;
                      }
                      //获取byte流对象的标准byte对象
                      byte[] b=bos.toByteArray();
                      //建立这个byte对象的ByteBuffer,并将数据存入
                      ByteBuffer byteBuffer=ByteBuffer.allocate(b.length);
                      byteBuffer.put(b);
                      //向客户端写入收到的数据
                      Write(byteBuffer);
                      //关闭客户端连接
                      sc.close();
                      //将本socket的事件在选择器中删除
                      key.cancel();
                     
                      System.out.println("连接结束");
                      System.out.println("=============================");
                  }
              }
          }
      }
      catch (Exception e)
      {
          e.printStackTrace();
      }
  }
  public boolean Write(ByteBuffer echoBuffer)
  {
      //将缓冲区复位以便于进行其他读写操作
      echoBuffer.flip();
      try
      {
          //向客户端写入数据,数据为接受到数据
          sc.write(echoBuffer);
      }
      catch (IOException e)
      {
          e.printStackTrace();
          return false;
      }
      System.out.println("返回数据: "+new String(echoBuffer.array()));
      return true;
  }
  public static void main(String[] args) {
		new Server().start();
	}
}
