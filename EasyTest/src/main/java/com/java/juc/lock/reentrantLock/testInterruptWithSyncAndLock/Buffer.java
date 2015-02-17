package com.java.juc.lock.reentrantLock.testInterruptWithSyncAndLock;

public interface Buffer {

	public void read() throws InterruptedException;
	public void write();
}
