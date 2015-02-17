package com.socket.nio.second.wr4;

public class Util {

	public static String[] btye2String(byte[] b ,int BLOCK){
	       int len = b.length;
	       int l = 0;
	       if(len%BLOCK==0){
	       	 l= len/BLOCK;
	       }else {
	       	l = len/BLOCK+1;
	       }
	       String[] arrayStr = new String[l];
	       for(int i=0;i<l;i++){ 
	       	int start = i*BLOCK;
	       	int end = 0;
	       	if(i==l-1)
	       		end = len;
	       	else{
	       	 end = (i+1)*BLOCK;
	       	}
	       	byte[] newB = new byte[BLOCK];
	       	int limit =0;
	       	for(int j=start,k=0;j<end;j++){
	       		newB[k++]=b[j];
	       		limit++;
	       	}
	       	arrayStr[i]=new String(newB,0,limit);
	       }
	       return arrayStr;
	}
}
