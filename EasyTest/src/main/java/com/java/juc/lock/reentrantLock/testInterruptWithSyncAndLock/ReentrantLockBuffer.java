package com.java.juc.lock.reentrantLock.testInterruptWithSyncAndLock;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockBuffer implements Buffer{
	private ReentrantLock lock = new ReentrantLock();   
	public void write() {   
    lock.lock();   
    try {   
        long startTime = System.currentTimeMillis();   
        System.out.println("开始往这个buff写入数据…");   
        for (;;)// 模拟要处理很长时间   
        {   
            if (System.currentTimeMillis()   
                    - startTime > 10000)   
                break;   
        }   
        System.out.println("终于写完了");   
    } finally {   
        lock.unlock();   
    }   
}   

public void read() throws InterruptedException {   
    lock.lockInterruptibly();// 注意这里，可以响应中断   
    try {   
        System.out.println("从这个buff读数据");   
    } finally {   
        lock.unlock();   
    }   
}   
}
