package com.socket.nio.simple;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class TestChannelClient {
	public static void main(String args[]) throws UnknownHostException, IOException{   
    Socket sc=new Socket("127.0.0.1",992);   
    OutputStream out=sc.getOutputStream();   
    out.write("hellofdrefdgggggggggggggggfd震天".getBytes());   
    out.flush();   
    sc.close();
}   

}
