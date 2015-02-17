package com.java.jvm;
class GCMemoryObject {  
    private byte[] bytes = null;  
    public GCMemoryObject(int multi) {  
 
        bytes = new byte[1024 * 256 * multi];  
 
 
    }  
}