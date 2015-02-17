package com.mina.myDecoder.v3;

import java.io.DataOutputStream;
import java.net.Socket;


public class Client {

	 public static void main(String[] args) {  
	        try {  
	            Socket socket = new Socket("127.0.0.1", 2500);  
	            DataOutputStream out =  new DataOutputStream( socket.getOutputStream() ) ; 
	            StringBuffer sb = new StringBuffer();
	            for(int j=0;j<1;j++){
	            	sb.append("raaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa").append(j);
	            }
	            String content = sb.toString();
	            System.out.println(content.getBytes().length);
	            Long start = System.currentTimeMillis();
	            for (int i = 0; i < 2; i++) {  
	            	content+=i;
	                out.write(content.getBytes()); 
	                out.write("\r\n".getBytes());
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
