package com.java.juc.lock.reentrantLock.testSyncAndLockCountTime;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockBeanchmark implements Counter {

    private volatile long count = 0;

    private Lock lock;

    public ReentrantLockBeanchmark() {
        // 使用非公平锁，true就是公平锁
        lock = new ReentrantLock(false);
    }

    public long getValue() {
        // TODO Auto-generated method stub
        return count;
    }

    public void increment() {
        lock.lock();
        try {
            count++;
        } finally {
            lock.unlock();
        }
    }



}
