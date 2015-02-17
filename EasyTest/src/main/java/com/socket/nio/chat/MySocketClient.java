package com.socket.nio.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author kevin
 */
public class MySocketClient implements Runnable{
    Selector selector;
    
     boolean running;
     
     SocketChannel sc;
     public MySocketClient(){
         running=true;
         
     }
     public void init() {
        try {
            sc = SocketChannel.open();
            sc.configureBlocking(false);
            sc.connect(new InetSocketAddress("localhost", 2345));
            
            
        } catch (IOException ex) {
            Logger.getLogger(MySocketClient.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
    
    public static void main(String[] args){
        
        MySocketClient client=new MySocketClient();
        new Thread(client).start();
    }
      
        
    public void execute(){
        
        int num=0;
        try {
            while (!sc.finishConnect()) {
            }
        } catch (IOException ex) {
            Logger.getLogger(MySocketClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        ReadKeyBoard rkb=new ReadKeyBoard();
        new Thread(rkb).start();
 
                               
          }
    public void run() {
        init();
        execute();
    }
  class ReadKeyBoard implements Runnable{
    boolean running2=true;
    public ReadKeyBoard(){
        
    }
    public void close(){
        running2=false;
    }
    public void run() {
        BufferedReader reader=new BufferedReader(new InputStreamReader(System.in));
        while(running2){
            try {
                System.out.println("enter some commands:");
            String str = reader.readLine();
            System.out.println("get commands:"+str);
            sc.write(ByteBuffer.wrap(str.getBytes()));
        } catch (IOException ex) {
            Logger.getLogger(ReadKeyBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    }
}
} 

