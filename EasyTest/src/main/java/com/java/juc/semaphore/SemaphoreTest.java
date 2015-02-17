package com.java.juc.semaphore;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**

 *Java 5.0里新增加了4个协调线程间进程的同步装置,它们分别是:Semaphore,CountDownLatch,CyclicBarrier和Exchanger

 *Semaphore可以控制运行线程的个数

 *Semaphore是一个用来管理资源池的工具,可以看成是个通行证,线程要想从资源池拿到资源必须先拿到通行证,如果线程暂时拿不到通告证,线程就会被阻断,进入等待状态.

 */

/**
 * 
 * 使用Semaphore的关键技术点如下:
 * 
 * 1.在构造Semaphore对象时,必须提供通行证的数目,如"newSemaphore(3)"将创建一个具有3个通行证的Semaphore对象,
 * 一旦该对象被创建,其通行证数量是不能改变的.
 * 
 * 2.Semaphore的acquire方法取得一个通行证,如果通行证已经发完了,当前线程将进入等待状态,直到有其他线程释放了通行证.
 * 
 * 3.Semaphore的release方法释放了资源池.
 */

public class SemaphoreTest {
	 /**

     * 模拟资源池的类

     * 只为池发放2个通行证,即同时只允许2个线程获得池中的资源

     */

    public static class Pool{

           ArrayList<String> pool = null;  //保存资源池中的资源

           Semaphore pass = null; //通行证

           Lock lock = new ReentrantLock(); 

           public Pool(int size){

                  //初始化资源池

                  pool = new ArrayList<String>();

                  for(int i = 0; i < size; i++){

                         pool.add("Resource " + i);

                  }

                  //发送2个通行证

                  pass = new Semaphore(2);

           }

           public String get()throws InterruptedException{

                  //获取通行证,只有得到通行证后才能得到资源

                  System.out.println("Try to get a pass...");

                  pass.acquire();

                  System.out.println("Got a pass");

                  return getResource();

           }

           private String getResource(){

                  lock.lock();

                  String result = pool.remove(0);

                  System.out.println("资源 " + result + "被取走");

                  lock.unlock();

                  return result;

           }

           public void put(String resource){

                  //归还通行证,并那还资源

                  System.out.println("Released a pass");

                  pass.release();

                  releaseResource(resource);

           }

           private void releaseResource(String resource){

                  lock.lock();

                  System.out.println("资源 " + resource + " 被归还");

                  pool.add(resource);

                  lock.unlock();

           }

    }

    public static void testPool(){

           //准备10个资源的资源池

           final Pool aPool = new Pool(10);

           Runnable worker = new Runnable(){

                  @Override

                  public void run() {

                         String resource = null;

                         try{

                                resource = aPool.get();//取得resource

                                //用resource做工作

                                System.out.println("I finished on " + resource);

                                Thread.sleep(500);

                                System.out.println("I finished on " + resource);

                         }catch(InterruptedException ex){

                         }

                         aPool.put(resource);

                  }                           

           };

           //启动5个任务

           ExecutorService service = Executors.newCachedThreadPool();

           for(int i = 0; i<5; i++){

                  service.submit(worker);

           }

           service.shutdown();

    }

    public static void main(String... args){

           SemaphoreTest.testPool();

    }


}
