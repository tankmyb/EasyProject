package com.mina.myDecoder.v2;

import java.io.DataOutputStream;
import java.net.Socket;


public class MyProtocalClient {
	 public static void main(String[] args) {  
	        try {  
	            Socket socket = new Socket("127.0.0.1", 2500);  
	            DataOutputStream out =  new DataOutputStream( socket.getOutputStream() ) ; 
	            StringBuffer sb = new StringBuffer();
	            for(int j=0;j<5;j++){
	            	sb.append("raaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa").append(j);
	            }
	            String content = sb.toString();
	            System.out.println(content.getBytes().length);
	            Long start = System.currentTimeMillis();
	            for (int i = 0; i < 10; i++) {  
	            	
	                MyProtocalPack pack=new MyProtocalPack((byte)i,content);  
	                System.out.println(pack.getLength()+"===");
	                out.writeInt(pack.getLength());  
	                out.write(pack.getFlag());  
	                out.write(pack.getContent().getBytes());  
	                out.flush();  
	                //System.out.println(i + " sended");  
	                //Thread.sleep(100);  
	            }  
	            System.out.println(System.currentTimeMillis()-start);
	            Thread.sleep(1000);  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	    }  
}
