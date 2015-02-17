package com.socket.nio.second.wr2;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Utils {

	public static void send(String echo,SocketChannel client,int size,ByteBuffer w_buff) throws IOException{
		 List<String> list = SplitStringByLength(echo,size);
		 for(Iterator it = list.iterator();it.hasNext();){
			 String str = (String)it.next();
			 w_buff.clear();
	  	     w_buff.put(str.getBytes());
	  	     w_buff.flip();
	         client.write(w_buff); 
		 }
		 
	}
	private static List<String> SplitStringByLength(String sample,
			int segmentLength) {
		List<String> segmentList = new ArrayList<String>();
		int regularSegmentCount = sample.length() / segmentLength;
		int charIndex = 0;
		for (int i = 0; i < regularSegmentCount; i++, charIndex += segmentLength) {
			//System.out.println(charIndex);
			String regularSegment = sample.substring(charIndex, segmentLength
					+ charIndex);
			//System.out.println(regularSegment);
			segmentList.add(regularSegment);
		}
		if (charIndex < sample.length()) {
			String lastSegment = sample.substring(charIndex);
			segmentList.add(lastSegment);
		}
		return segmentList;
	}

}
