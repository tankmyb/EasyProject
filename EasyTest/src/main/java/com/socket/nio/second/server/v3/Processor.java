package com.socket.nio.second.server.v3;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Processor implements Runnable {
	private SocketChannel client = null;
	private ByteBuffer headBuffer = null;
	private ByteBuffer bodyBuffer = null;

	public Processor(SocketChannel client, ByteBuffer headBuffer,
			ByteBuffer bodyBuffer) {
		this.client = client;
		this.headBuffer = headBuffer;
		this.bodyBuffer = bodyBuffer;
	}

	private void Write() throws IOException {
		System.out.println("headBuffer:" + headBuffer);
		System.out.println("bodyBuffer:" + bodyBuffer);
		// 设置缓冲区为写出。flip()：position(0),limit(old position)
		headBuffer.flip();
		bodyBuffer.flip();
		try {
			while (headBuffer.hasRemaining())
				client.write(headBuffer);
			while (bodyBuffer.hasRemaining())
				client.write(bodyBuffer);
		} catch (IOException e) {
			client.close();
			throw e;
		}
	}

	public void run() {
		try {
			this.Write();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
