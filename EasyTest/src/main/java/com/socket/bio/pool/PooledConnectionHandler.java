package com.socket.bio.pool;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class PooledConnectionHandler implements Runnable {   
    protected Socket connection;   
    protected static List pool = new LinkedList();   
    public PooledConnectionHandler() {}   
    public void  handleConnection() {  
    	System.out.println(connection);
        try {   
            PrintWriter streamWriter = new PrintWriter(connection.getOutputStream());    
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));    
            String line = null;   
            while((line=streamReader.readLine())!=null){
            	System.out.println(line);
            	streamWriter.println(line); 
            }
                   
            streamWriter.close();   
            streamReader.close();   
        }   
        catch(FileNotFoundException e) {   
            System.out.println("");   
        }   
        catch(IOException e) {   
            System.out.println(""+e);   
        }   
    }   
    public static void processRequest(Socket requestToHandle) {   
        synchronized(pool) {  
        	
            pool.add(pool.size(), requestToHandle);   
            pool.notifyAll();
            System.out.println(pool.size()+"=====");
        }   
    }   
    public void run() {   
        while(true) {   
            synchronized(pool) {   
                while(pool.isEmpty()) {   
                    try {   
                        pool.wait();   
                    }   
                    catch(InterruptedException e) {   
                        e.printStackTrace();    
                    }   
                }   
                connection= (Socket)pool.remove(0);    
            }   
            handleConnection();   
        }   
    }   


}
