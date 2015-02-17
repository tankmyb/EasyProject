package com.socket.bio.udp.multicast.v3;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;


public class DataPacket {
	public static final int DataSwapSize = 1024;
    public ArrayList list;
    public int len=0;
    public ArrayList list1 = new ArrayList();
    public int len1=0;
    public DataPacket(){}
	public DataPacket(String fileName) {
		BufferedInputStream in = null;
		try {
			in = new BufferedInputStream(new FileInputStream(
					fileName));
			ByteArrayOutputStream out = new ByteArrayOutputStream(1024);         

			System.out.println("Available bytes:" + in.available());

			byte[] temp = new byte[DataSwapSize];
			int size = 0;
			while ((size = in.read(temp)) != -1) {
				//out.write(temp, 0, size);
				byte[] b = new byte[size];
				for(int i=0;i<size;i++){
					b[i] = temp[i];
				}
				list.add(b);
				len+=size;
			}
			in.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    public void add(byte[] b){
    	list1.add(b);
    	len1+=b.length;
    }
    public boolean isFull(){
    	return len1>=len;
    }
	public ArrayList getDataPackets() throws Exception {
		return list;
		
	}

	public static void main(String[] args) throws Exception{
		/*String sample = "字符串按照长度拆分算法， 比如说长度为182的字符串按照60个一条要拆分成4条";
		int segmentLength = 6;
		System.out.println(sample.length());
		List<String> segmentList = SplitStringByLength(sample, segmentLength);

		for (String segment : segmentList) {
			System.out.println(segment);
		}*/
		DataPacket dp = new DataPacket("D:\\order.sql");
		ArrayList list = dp.getDataPackets();
		for(Iterator it = list.iterator();it.hasNext();){
			byte[] b = (byte[])it.next();
			System.out.println(new String(b));
		}
	}
	public byte[] Gereratedata(){
		byte[] b = new byte[len1];
		int i=0;
		for(Iterator it = list1.iterator();it.hasNext();){
			byte[] by = (byte[])it.next();
			for(int j=0;j<by.length;j++){
				b[i++]=by[j];
			}
		}
		return b;
	}

	private static ArrayList<String> SplitStringByLength(String sample,
			int segmentLength) {
		ArrayList<String> segmentList = new ArrayList<String>();
		int regularSegmentCount = sample.length() / segmentLength;
		int charIndex = 0;
		for (int i = 0; i < regularSegmentCount; i++, charIndex += segmentLength) {
			System.out.println(charIndex);
			String regularSegment = sample.substring(charIndex, segmentLength
					+ charIndex);
			System.out.println(regularSegment);
			segmentList.add(regularSegment);
		}
		if (charIndex < sample.length()) {
			String lastSegment = sample.substring(charIndex);
			segmentList.add(lastSegment);
		}
		return segmentList;
	}

}
