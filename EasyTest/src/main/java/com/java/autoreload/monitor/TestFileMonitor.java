package com.java.autoreload.monitor;
public class TestFileMonitor {  
  
    public static void main(String args[]){  
        FileObserver ob = new FileObserver("D:\\log");  
        FileListener listener = new FileListener();  
        ob.addListener(listener);  
        FileMonitor monitor = new FileMonitor(ob);  
        monitor.start();  
    }  
}  