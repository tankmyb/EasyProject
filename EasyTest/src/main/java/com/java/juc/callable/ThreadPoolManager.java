package com.java.juc.callable;

/** 
线程管理类 
*/ 
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class ThreadPoolManager 
{ 
	private static ThreadPoolManager poolManager = new ThreadPoolManager();

    // 线程池维护线程的最少数量 
    private final static int CORE_POOL_SIZE = 200; 

    // 线程池维护线程的最大数量 
    private final static int MAX_POOL_SIZE = 200; 

    // 线程池维护线程所允许的空闲时间 
    private final static int KEEP_ALIVE_TIME = 180; 

    // 线程池所使用的缓冲队列大小 
    private final static int WORK_QUEUE_SIZE = 500; 

    // 请求Request缓冲队列 
   public Queue<Runnable> msgQueue = new LinkedList<Runnable>(); 

   private ArrayBlockingQueue workQueue =  new ArrayBlockingQueue( WORK_QUEUE_SIZE );
   
    //handler - 由于超出线程范围和队列容量而使执行被阻塞时所使用的处理程序 
    final RejectedExecutionHandler handler = new RejectedExecutionHandler() 
    { 
        public void rejectedExecution( Runnable r, ThreadPoolExecutor executor ) 
        { 
            //System.out.println(r+" request 放入队列中重新等待执行 "+r ); 
            msgQueue.offer( r  ); 
        } 
    }; 


    // 管理线程池 
    final ThreadPoolExecutor threadPool = new ThreadPoolExecutor( 
            CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.SECONDS, 
            workQueue,handler); 

   
    
    
    /** 
     * 根据key取得对应实例 
     * @param key 
     * @return 
     */ 
    public static synchronized ThreadPoolManager getInstance() 
    { 
        return poolManager; 
    } 

    private ThreadPoolManager(){
    } 

    public Future<String> addTask( Callable<String> task ) 
    { 
       return threadPool.submit( task ); 
    } 
    public boolean execute( Runnable task ) 
    { 
    	if(workQueue.size()>=500){
    		//System.out.println("too");
    		return false;
    	}
        threadPool.submit( task ); 
        return true;
    } 
    public void shutdown(){
    	threadPool.shutdown();
    }
    public long getTaskSize(){
    	System.out.println("====================start");
    	System.out.println(threadPool.getActiveCount());
    	System.out.println(threadPool.getCompletedTaskCount());
    	System.out.println(threadPool.getTaskCount());
    	System.out.println(threadPool.getLargestPoolSize());
    	System.out.println("====================end");
    	return 0;
    }
} 


