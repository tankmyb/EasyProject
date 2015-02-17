package com.socket.bio.pool;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

public class PooledRemoteFileServer {
	protected int maxConnections;   
    protected int listenPort;   
    protected ServerSocket serverSocket;   
    public PooledRemoteFileServer(int aListenPort, int maxConnections) {   
        listenPort= aListenPort;   
        this.maxConnections = maxConnections;   
    }   
    public void acceptConnections() {   
        try {   
            ServerSocket server = new ServerSocket(listenPort, 5);   
            Socket incomingConnection = null;   
            while(true) {   
            	
                incomingConnection = server.accept();   
                System.out.println("=========="+incomingConnection);
                handleConnection(incomingConnection);   
            }   
        }   
        catch(BindException e) {   
            System.out.println("");   
        }   
        catch(IOException e) {   
            System.out.println(""+listenPort);   
        }   
    }   
    protected void handleConnection(Socket connectionToHandle) {   
        PooledConnectionHandler.processRequest(connectionToHandle);   
    }   
    public void setUpHandlers() {   
        for(int i=0; i<maxConnections; i++) {   
            PooledConnectionHandler currentHandler = new PooledConnectionHandler();   
            new Thread(currentHandler, "Handler " + i).start();   
        }   
    }   
    public static void main(String args[]) {   
        PooledRemoteFileServer server = new PooledRemoteFileServer(1001, 3);   
        server.setUpHandlers();    
        server.acceptConnections();   
    }   

}
