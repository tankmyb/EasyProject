package com.java.thread.testThread.testThreadPool;



import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程管理类 
 * @author dengsq
 *
 */
public class ThreadPoolManager 
{ 
	private static ThreadPoolManager poolManager ;

    // 线程池维护线程的最少数量 
    private final static int CORE_POOL_SIZE = 5; 

    // 线程池维护线程的最大数量 
    private final static int MAX_POOL_SIZE = 200; 

    // 线程池维护线程所允许的空闲时间 
    private final static int KEEP_ALIVE_TIME = 180; 

    // 线程池所使用的缓冲队列大小 
    private final static int WORK_QUEUE_SIZE = 500; 

   
    
   
    //handler - 由于超出线程范围和队列容量而使执行被阻塞时所使用的处理程序 
    final RejectedExecutionHandler handler = new RejectedExecutionHandler() 
    { 
        public void rejectedExecution( Runnable r, ThreadPoolExecutor executor ) 
        { 
            //System.out.println(r+" request 放入队列中重新等待执行 "+r ); 
            //msgQueue.offer( r  ); 
        } 
    }; 


    // 管理线程池 
    @SuppressWarnings({ "unchecked", "rawtypes" })
	final ThreadPoolExecutor threadPool = new ThreadPoolExecutor( 
            CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.SECONDS, 
            new ArrayBlockingQueue( WORK_QUEUE_SIZE ),this.handler); 

   
    
    
    /** 
     * 根据key取得对应实例 
     * @param key 
     * @return 
     */ 
    public static synchronized ThreadPoolManager getInstance() 
    { 
    	if(poolManager == null)
    		poolManager = new ThreadPoolManager();
        return poolManager; 
    } 

    private ThreadPoolManager(){} 



    public Future<String> submit( Callable<String> task ) 
    { 
        return threadPool.submit( task ); 
    } 
    public void shutdown(){
    	threadPool.shutdown();
    }
    /**
	 * 线程池中被cancel的线程释放掉
	 * @param  task	调用的入参，任务
	 * @return  void		返回空
	 * @throws  
	 */
    public void purge(Future<String> task){
		threadPool.purge();
		threadPool.remove((Runnable) task);
	}
} 


