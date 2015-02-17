package com.socket.nio.nioSession;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/** 
 *  
 * @author Ritsky 
 * 
 * @param <Element> 
 */  
public class Queue<Element> {  
    private int arraySize = 100;  
    private Element[] data = null;  
    private int first = 0;  
    private int next = 0;  
      
    private Lock lock = new ReentrantLock();  
      
    private Condition notFull  = lock.newCondition();    
    private Condition notEmpty = lock.newCondition();  
  
      
    public Queue(int size){  
        arraySize = size;  
          
        // reset    
        empty();  
          
           
    }  
      
    @SuppressWarnings("unchecked")  
    public void empty(){  
        lock.lock();  
        try {  
            data = (Element[]) new Object[arraySize];  
  
            // Set the pointer   
            first = 0;  
            next = 0;  
        }  
        finally{  
            lock.unlock();  
        }  
    }  
      
    /** 
     * Sample case: (assume arraySize=5) 
     *      first=0 next=1  -> size = 1 (5-0+1) % 5 = 6 % 5 =1  
     *      first=1 next=0  -> size = 4 (5-1+4) % 5 = 9 % 5 =4 
     *      first=4 next=0  -> size = 1 (5-4+0) % 5 = 1 
     *      first=3 next=3  -> size = 0 (5-3+3) % 5 = 5 % 5 = 0  
     *  
     *  
     *  
     * @return 
     */  
    public int size(){  
        lock.lock();  
        try {  
            return (arraySize - first + next ) % arraySize;  
        }  
        finally {  
            lock.unlock();  
        }  
    }  
      
    public boolean isEmpty(){  
        lock.lock();  
        try {  
            return (first == next);  
        }  
        finally {  
            lock.unlock();  
        }  
    }  
      
    /** 
     * Maximum entity can be stored = arraySize-1 
     * Always leave one slot 
     *   
     * @return 
     */  
    public boolean isFull(){  
        lock.lock();  
        try {  
            if(size() == arraySize - 1){  
                return true;  
            }  
              
            return false;  
        }  
        finally {  
            lock.unlock();  
        }  
    }  
      
    public void enQueue(Element input) throws Exception{  
        lock.lock();  
        try {  
            if(isFull()){  
                throw new RuntimeException("overflow");  
            }  
  
            data[next] = input;  
            next = (next + 1) % arraySize;  
        }  
        finally {  
            lock.unlock();  
        }  
    }  
      
    public Element deQueue() throws Exception{   
        lock.lock();  
        try {  
            if(isEmpty()){  
                throw new RuntimeException("Queue empty");  
            }  
  
            Element result = data[first];  
            data[first] = null;     // not necessary   
  
            first++;  
            if(first >= arraySize){  
                first = 0;  
            }  
  
            return result;  
        }  
        finally {  
            lock.unlock();  
        }  
    }  
      
      
    public void enQueueNotify(Element input) throws Exception {  
        if(input == null) {  
            throw new NullPointerException("input is null");  
        }  
          
          
        lock.lock();  
        try {  
            if(isFull()) {  
                notFull.await();  
            }  
              
            if(isFull()) {  
                throw new Exception("overflow");  
            }  
  
            data[next] = input;  
            next = (next + 1) % arraySize;  
  
            notEmpty.signal();  
        }  
        finally {  
            lock.unlock();  
        }  
    }  
      
    public Element deQueueWait(long waitTime) throws InterruptedException {  
        // Wait if the queue is empty   
        lock.lock();  
        try {  
            if(isEmpty()) {  
                if(waitTime <= 0) {  
                    notEmpty.await();  
                }  
                else {  
                    notEmpty.await(waitTime, TimeUnit.MILLISECONDS);  
                }  
                  
                if(isEmpty()) {  
                    return null;  
                }  
            }  
              
              
            Element result = data[first];  
            data[first] = null;     // not necessary   
  
            first++;  
            if(first >= arraySize){  
                first = 0;  
            }  
  
            notFull.signal();  
              
            return result;  
        }  
        finally {  
            lock.unlock();  
        }  
    }  
  
      
    public String toString(){  
        StringBuffer sb = new StringBuffer();  
          
        sb.append("first=" + first + " next=" + next);  
        sb.append("/n");  
        for(int i=0; i<data.length; i++){  
            sb.append(i + "/" + data[i]);  
            sb.append(" ");  
        }  
        sb.append("/n");  
          
        return sb.toString();  
    }  
}  

