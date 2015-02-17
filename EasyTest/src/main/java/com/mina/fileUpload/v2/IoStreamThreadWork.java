package com.mina.fileUpload.v2;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class IoStreamThreadWork extends Thread{  
    public static final int BUFFER_SIZE = 1024*2;  
      
    private BufferedInputStream bis;  
    private BufferedOutputStream bos;  
      
      
    public BufferedInputStream getBis() {  
        return bis;  
    }  
  
    public void setBis(BufferedInputStream bis) {  
        this.bis = bis;  
    }  
  
    public BufferedOutputStream getBos() {  
        return bos;  
    }  
  
    public void setBos(BufferedOutputStream bos) {  
        this.bos = bos;  
    }  
  
    public IoStreamThreadWork(InputStream in, OutputStream os){  
        bis = new BufferedInputStream(in);  
        bos = new BufferedOutputStream(os);  
    }  
    public synchronized void run() {  
        byte[] bufferByte = new byte[BUFFER_SIZE];  
        int tempData = 0;  
        try {  
            while(true){  
            	//Thread.sleep(3L);
            	tempData = bis.read(bufferByte);
            	if(tempData ==-1){
            		break;
            	}
                bos.write(bufferByte, 0, tempData);  
                bos.flush();  
            }  
              
            
            
        } catch (IOException e) {  
            e.printStackTrace();  
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{  
            try {  
                bos.close();  
                bis.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    }  

}
