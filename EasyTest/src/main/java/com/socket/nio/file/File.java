package com.socket.nio.file;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

import org.junit.Test;

import com.socket.nio.second.CharsetUtils;

public class File {
	@Test
	public void test1() throws Exception {

		FileInputStream fis = new FileInputStream("D:/ADSL.txt");

		// 得到文件通道

		FileChannel fc = fis.getChannel();

		// 分配与文件尺寸等大的缓冲区

		ByteBuffer bf = ByteBuffer.allocate((int) fc.size());

		// 整个文件内容全读入缓冲区，即是内存映射文件

		fc.read(bf);

		// 把缓冲中当前位置回复为零

		bf.rewind();

		// 输出缓冲区中的内容

		while (bf.hasRemaining()) {

			System.out.print((char) bf.get());

		}

	}

	@Test
	public void test2() throws Exception {
		FileInputStream fis = new FileInputStream("D:/ADSL.txt");

		// 得到文件通道

		FileChannel fc = fis.getChannel();

		// 指定大小为 1024 的缓冲区

		ByteBuffer bf = ByteBuffer.allocate(1024);

		// 读取通道中的下一块数据到缓冲区中

		// 缓冲区的 position 即为当前缓冲区中最后有效位置

		while (fc.read(bf) != -1) {

			// 把缓冲中当前位置回复为零，前把缓冲区的 limit 设置为之前 position 值

			bf.flip();

			// 输出缓冲区中的内容

			while (bf.hasRemaining()) {

				System.out.print((char) bf.get());

			}

			// 清理缓冲区，准备再次读取数据

			bf.clear();

		}

	}

	@Test
	public void test3() throws Exception {
		FileOutputStream fos = new FileOutputStream("D:/nio.txt");

		// 得到文件通道

		FileChannel fc = fos.getChannel();

		// 指定大小为 1024 的缓冲区

		ByteBuffer bf = ByteBuffer.allocate(1024);

		// 要写入文件的字符串

		String greeting = "Hello, Java NIO";

		// 把以上字符串逐字放入缓冲区

		for (int i = 0; i < greeting.length(); i++) {

			bf.putChar(greeting.charAt(i));

		}

		// 记得执行这个方法，使得 position=0, limit=30, 才能写入正确的数据

		// 否则 position 为 30, limit 为 1024，将会把 30 之后的全部空数据(0) 填到文件中

		bf.flip();

		// 缓冲区数据写入到文件中，会把缓冲区中从 position 到 limit 之间的数据写入文件

		// fc.write(bf);//会乱码

		fc.write(CharsetUtils.UTF8.encode(greeting));

		fc.close(); // 关闭文件通道

		fos.close(); // 关闭文件输出流

	}

	/**
	 * ByteBuffer to String
	 */
	@Test
	public void decode() {
		String str = "内容";
		ByteBuffer buffer = ByteBuffer.wrap(str.getBytes());
		//buffer.flip();
		System.out.println(" buffer= " + buffer);
		Charset charset = null;
		CharsetDecoder decoder = null;
		CharBuffer charBuffer = null;
		try {
			charset = Charset.forName("UTF8");
			decoder = charset.newDecoder();
			
			charBuffer = decoder.decode(buffer);
			System.out.println(" charBuffer= " + charBuffer);
			System.out.println(charBuffer.toString());
		
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
