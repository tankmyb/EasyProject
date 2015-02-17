package com.socket.nio.nioSession;

import java.io.File;

import org.apache.log4j.PropertyConfigurator;


public class BootStart {
	/** 
   * @param args 
   */  
  public static void main(String[] args) {  
      //initLog4J();  
        
      try {  
          //ClientServer clientServer = ClientServer.start();   
          MultiNioServer mobileServer = MultiNioServer.startServer();  
            
          System.out.println("启动成功......");  
      } catch (Exception e) {  
          // TODO Auto-generated catch block   
          e.printStackTrace();  
      }  
  }  
    
    
  public static void initLog4J() {  
      //String prefix =  System.getProperty("user.dir");  
        
      File file = new File(ClassLoader.getSystemResource("").getPath()+"/log4j.properties");  
        
      if(file.exists() == false){  
          System.out.println("ERROR: log4j cfg not found: "+ file.toString());  
          return;     // Not need to configure   
      }  
        

      // Configure the log4j using the given path   
      String path = file.getAbsolutePath();  
      PropertyConfigurator.configure(path);     
  }  


}
