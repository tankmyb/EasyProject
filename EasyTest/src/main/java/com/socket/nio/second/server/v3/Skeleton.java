package com.socket.nio.second.server.v3;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Skeleton {
	private static int headLength = 2; // byte[]转换为int

	public static int byteArrayToInt(byte[] b) {
		int mask = 0xff;
		int temp = 0;
		int n = 0;
		for (int i = 0; i < b.length; i++) {
			n <<= 8;
			temp = b[i] & mask;
			n |= temp;
		}
		return n;
	}

	public static void main(String args[]) throws IOException {
		System.out.println("begin fuck"); // 线程池，CACHE类型的
		Executor executor = Executors.newCachedThreadPool();
		// 定义一个selector
		Selector selector = Selector.open();
		// 定义一个服务端SOCKET信道
		ServerSocketChannel server = ServerSocketChannel.open();
		server.socket().bind(new InetSocketAddress("127.0.0.1", 8001));
		server.configureBlocking(false);
		// 把selector与信道进行关联，信道可进行accept操作
		server.register(selector, SelectionKey.OP_ACCEPT);
		int counter = 0;
		while (true) {
			if (0 == selector.select()) { // 也可以设置超时时间
				continue; // 定义一个选择键，水平触发，如果事件不处理，下次依然会有通知；没有边沿触发机制
			}
			Iterator<SelectionKey> it = selector.selectedKeys().iterator();
			while (it.hasNext()) {
				SelectionKey key = it.next();
				SocketChannel client = null;
				try {
					// accept事件
					if (key.isAcceptable()) {
						System.out.println("key.isAcceptable()");
						client = ((ServerSocketChannel) key.channel()).accept();
						// 一定要非阻塞型的。epoll中的水平触发阻塞与非阻塞的都可以
						client.configureBlocking(false);
						client.register(key.selector(), SelectionKey.OP_READ);
						/* * 是不行的，由于JAVA的水平触发行为，如果在线程中还没进行读的话，下次又会被select出来 */
					} else if (key.isReadable()) {

						System.out.println("########################"
								+ ++counter + "########################");
						System.out.println("key.isReadable()");
						client = (SocketChannel) key.channel(); // 读取消息头
						ByteBuffer headBuffer = ByteBuffer.allocate(headLength);
						int byteRead = client.read(headBuffer);
						if (0 < byteRead) {
							final int msgLength = byteArrayToInt(headBuffer
									.array());
							System.out.println("bodyLength:" + msgLength);
							ByteBuffer bodyBuffer = ByteBuffer
									.allocate(msgLength);
							int recvBytes = 0;
							int totalBytes = 0;
							while (totalBytes < msgLength) {
								if (-1 == (recvBytes = client.read(bodyBuffer))) {
									throw new IOException(
											"read bodyBuffer error");
								}
								totalBytes += recvBytes; // 线程池，该做什么做什么去
								executor.execute(new Processor(client,
										headBuffer, bodyBuffer));
							}

						} else if (-1 == byteRead) {
							// error, socket句柄要关闭，节省系统资源
							client.close();
						}
					}
				} catch (Exception e) {
					// 处理socket非优雅型地异常关闭
					client.close();
					e.printStackTrace(); // 迭代子就是这么删除的，C++的小盆友们可能会想不通
					it.remove();
				}
			}
		}
	}

}