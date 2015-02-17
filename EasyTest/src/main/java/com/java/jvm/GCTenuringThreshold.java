package com.java.jvm;
public class GCTenuringThreshold {  
    public static void main(String[] args) throws Exception {  
        GCMemoryObject object1 = new GCMemoryObject(2);  
        GCMemoryObject object2 = new GCMemoryObject(8);  
        GCMemoryObject object3 = new GCMemoryObject(8);  
        GCMemoryObject object4 = new GCMemoryObject(8);  
        object2 = null;  
        object3 = null;  
        GCMemoryObject object5 = new GCMemoryObject(8);  
        Thread.sleep(4000);  
        object2 = new GCMemoryObject(8);  
        object3 = new GCMemoryObject(8);  
        object2 = null;  
        object3 = null;  
        object5 = null;  
        GCMemoryObject object6 = new GCMemoryObject(8);  
        Thread.sleep(5000);  
 
        GCMemoryObject object7 = new GCMemoryObject(8);  
        GCMemoryObject object8 = new GCMemoryObject(8);  
        GCMemoryObject object9 = new GCMemoryObject(8);  
        GCMemoryObject object10 = new GCMemoryObject(8);  
        //object6 = null;  
        Thread.sleep(5000);  
        System.out.println("ok");  
    }  
}