package com.socket.nio.calculator;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

import com.socket.nio.second.CharsetUtils;

public class Client {
	private SocketChannel sc;
	private final int MAX_LENGTH = 1024;
	private ByteBuffer r_buff = ByteBuffer.allocate(MAX_LENGTH);
	private ByteBuffer w_buff = ByteBuffer.allocate(MAX_LENGTH);
	private static String host = "127.0.0.1";
	private static int port = 8850;

	public Client() throws Exception {
		Set<SelectionKey> selectionKeys;
		Iterator<SelectionKey> iterator;
		SelectionKey selectionKey;
		SocketChannel client = null;
		try {
			InetSocketAddress addr = new InetSocketAddress(host, port);
			// 生成一个socketchannel
			sc = SocketChannel.open();

			sc.configureBlocking(false);
			// 打开选择器
			Selector selector = Selector.open();
			// 注册连接服务端socket动作
			sc.register(selector, SelectionKey.OP_CONNECT);
			// 连接
			sc.connect(addr);
			// 分配缓冲区大小内存

			
			FOR: while (true) {
				if (!sc.isOpen())
					break;
				// 选择一组键，其相应的通道已为 I/O 操作准备就绪。
				// 此方法执行处于阻塞模式的选择操作。
				selector.select();
				// 返回此选择器的已选择键集。
				selectionKeys = selector.selectedKeys();
				// System.out.println(selectionKeys.size());
				iterator = selectionKeys.iterator();
				Scanner scanner = new Scanner(System.in);
				String echo = null;
				int count = 0;

				while (iterator.hasNext()) {
					selectionKey = iterator.next();
					if (selectionKey.isConnectable()) {
						System.out.println("client connect");
						client = (SocketChannel) selectionKey.channel();
						// 判断此通道上是否正在进行连接操作。
						// 完成套接字通道的连接过程。
						if (client.isConnectionPending()) {
							client.finishConnect();
							System.out.println("完成连接!");
							echo = scanner.nextLine();
							// 输入回射消息

							// 把回射消息放入w_buff中
							w_buff.clear();
							w_buff.put(echo.getBytes());
							w_buff.flip();
							client.write(w_buff);
						}
							// client.write(ByteBuffer.wrap(echo.getBytes()));
							client.register(selector, SelectionKey.OP_READ);

						} else if (selectionKey.isReadable()) {
							client = (SocketChannel) selectionKey.channel();
							r_buff.clear();
							count = client.read(r_buff);
							r_buff.flip();
							byte[] temp = new byte[r_buff.limit()];
							r_buff.get(temp);
							System.out
									.println("count:"
											+ count
											+ ",receive:"
											+ CharsetUtils.UTF8.decode(temp)
													.toString());
							client.register(selector, SelectionKey.OP_WRITE);

						} else if (selectionKey.isWritable()) {
							client = (SocketChannel) selectionKey.channel();
							echo = scanner.nextLine();

							// 把回射消息放入w_buff中
							w_buff.clear();
							w_buff.put(echo.getBytes());
							w_buff.flip();
							client.write(w_buff);
							if (!echo.equals("end")) {
								client.register(selector, SelectionKey.OP_READ);
							} else {
								break FOR;
							}

						}
					}
					selectionKeys.clear();
				}

		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			client.close();
			sc.close();
		}

	}

	
	public static void main(String args[]) throws Exception {
		new Client();
	}

}
