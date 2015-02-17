package com.java.thread.callback.v1;

public class Local implements CallBack,Runnable{  
    
  /** 
   * 远程接收消息的类，模拟point-to-point 
   */  
  private Remote remote;  
    
  /** 
   * 发送出去的消息 
   */  
  private String message;  
    
  public Local(Remote remote, String message) {  
      super();  
      this.remote = remote;  
      this.message = message;  
  }  

  /** 
   * 发送消息 
   */  
  public void sendMessage()  
  {  
      /**当前线程的名称**/  
      System.out.println(Thread.currentThread().getName());  
      /**创建一个新的线程发送消息**/  
      Thread thread = new Thread(this);  
      thread.start();  
      /**当前线程继续执行**/  
      System.out.println("Message has been sent by Local~!");  
  }  

  /** 
   * 发送消息后的回调函数 
   */  
  public void execute(Object... objects ) {  
      /**打印返回的消息**/  
      System.out.println(objects[0]);  
      /**打印发送消息的线程名称**/  
      System.out.println(Thread.currentThread().getName());  
      /**中断发送消息的线程**/  
      Thread.interrupted();  
  }  
    
  public static void main(String[] args)  
  {  
      Local local = new Local(new Remote(),"Hello");  
        
      local.sendMessage();  
  }  

  public void run() {  
      remote.executeMessage(message, this);  
        
  }  
}  
