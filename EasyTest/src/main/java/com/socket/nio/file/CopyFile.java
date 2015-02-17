package com.socket.nio.file;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class CopyFile {
	static public void main(String args[]) throws Exception {

		String infile = "D:/Issue Log 8Mar2011.xls";

		String outfile = "D:/aa/aaa.xls";

		FileInputStream fin = new FileInputStream(infile);

		FileOutputStream fout = new FileOutputStream(outfile);

		FileChannel fcin = fin.getChannel();

		FileChannel fcout = fout.getChannel();

		ByteBuffer buffer = ByteBuffer.allocate(1024);

		while (true) {

			buffer.clear();

			int r = fcin.read(buffer);

			if (r == -1) {

				break;

			}

			buffer.flip();

			fcout.write(buffer);
		}
		fcin.close();
     fin.close();
     fcout.close();
     fout.close();
	}

}
