package com.netty.simple;
public class RunServer {  
  
    public static void main(String[] args) {  
          
        
        final NettyServer server =new NettyServer();  
          
        Runtime.getRuntime().addShutdownHook(new Thread() {  
  
            @Override  
            public void run() {  
                try {  
                    server.stop();  
                } catch (Exception e) {  
                    System.out.println("run main stop error!");  
                }  
            }  
  
        }  );  
        server.init();  
        server.start();  
    }  
  
}  