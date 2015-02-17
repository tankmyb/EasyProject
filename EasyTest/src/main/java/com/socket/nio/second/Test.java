package com.socket.nio.second;


public class Test {
   public static void main(String[] args){


      int BLOCK = 40;
	   byte[] b = "Hello,Server顶替在佣兵顶替在佣兵顶替佣顶替在佣兵顶替在佣兵顶替佣顶替在佣兵顶替在佣兵顶替佣顶替在佣兵顶替在佣兵顶替佣顶替在佣兵顶替在佣兵顶替佣顶替在佣兵顶替在佣兵顶替佣顶替在佣兵顶替在佣兵顶替佣".getBytes();
       int len = b.length;
       int l = 0;
       if(len%BLOCK==0){
       	 l= len/BLOCK;
       }else {
       	l = len/BLOCK+1;
       }
       System.out.println(len);
       for(int i=0;i<l;i++){ 
       	int start = i*BLOCK;
       	int end = 0;
       	if(i==l-1){
       	 end = len;
       	}else{
       	 end = (i+1)*BLOCK;
       	}
       	System.out.println(start+"===="+end);
       	byte[] newB = new byte[40];
       	int limit =0;
       	for(int j=start,k=0;j<end;j++){
       		newB[k++]=b[j];
       		limit++;
       	}
        System.out.println(new String(newB,0,limit));
       }
   }
}
