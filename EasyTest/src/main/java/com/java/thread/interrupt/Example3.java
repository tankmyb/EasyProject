package com.java.thread.interrupt;

/**
 * 当线程等待某些事件发生而被阻塞，又会发生什么？当然，如果线程被阻塞，它便不能核查共享变量，也就不能停止。这在许多情况下会发生，例如调用Object.
 * wait()、ServerSocket.accept()和DatagramSocket.receive()时，这里仅举出一些。
 * 
 * 他们都可能永久的阻塞线程。即使发生超时，在超时期满之前持续等待也是不可行和不适当的，所以，要使用某种机制使得线程更早地退出被阻塞的状态。
 * 
 * 很不幸运，不存在这样一种机制对所有的情况都适用，但是，根据情况不同却可以使用特定的技术。在下面的环节，我将解答一下最普遍的例子。
 * 
 * 使用Thread.interrupt()中断线程
 * 
 * 正如Listing
 * A中所描述的，Thread.interrupt()方法不会中断一个正在运行的线程。这一方法实际上完成的是，在线程受到阻塞时抛出一个中断信号
 * ，这样线程就得以退出阻塞的状态。更确切的说，如果线程被Object.wait,
 * Thread.join和Thread.sleep三种方法之一阻塞，那么，它将接收到一个中断异常
 * （InterruptedException），从而提早地终结被阻塞状态。
 * 
 * 因此，如果线程被上述几种方法阻塞，正确的停止线程方式是设置共享变量，并调用interrupt()（注意变量应该先设置）。如果线程没有被阻塞，
 * 这时调用interrupt
 * ()将不起作用；否则，线程就将得到异常（该线程必须事先预备好处理此状况），接着逃离阻塞状态。在任何一种情况中，最后线程都将检查共享变量然后再停止
 * 。Listing C这个示例描述了该技术。
 * 
 * 
 * @author Administrator
 * 
 */
public class Example3 extends Thread {

	volatile boolean stop = false;
	volatile int i=0;
	public static void main(String args[]) throws Exception {

		Example3 thread = new Example3();

		System.out.println("Starting thread...");

		thread.start();

		Thread.sleep(3000);

		System.out.println("Asking thread to stop...");

		thread.stop = true;// 如果线程阻塞，将不会检查此变量

		
		

		Thread.sleep(3000);

		thread.interrupt();
		
		System.out.println("Stopping application...");

		// System.exit( 0 );

	}

	public void run() {

		while (!stop) {

			System.out.println("Thread running...");

			try {

				Thread.sleep(1000);
        if(i++==3){
        	synchronized (this) {
        		this.wait();
					}
        	
        	
        }
        
			} catch (InterruptedException e) {

				System.out.println("Thread interrupted...");

			}

		}

		System.out.println("Thread exiting under request...");

	}

}
