package com.java.juc.cyclicBarrier;

import java.util.Random;
import java.util.concurrent.CyclicBarrier;
/**

 * CyclicBarrier维持一个计数器,与CountDownLatch不同的是,等待这个CyclicBarrier的线程必须等到计数器的某个值时,才可以继续.

 * CyclicBarrier就像它名字的意思一样,可看成是个障碍,所有的线程必须到齐后才能一起通过这个障碍.

 */

/**

 * 本实例实现一个数组相邻元素的加法,一个线程给数组的第一个元素赋值,然后等待其它线程给数组第二个元素赋值,然后将第一个元素和第二个元素相加.

 */

/**

 * CyclicBarrier的关键技术点如下:

 * 1.构造CyclicBarrier对象时,需要指定计数器的目标值,计数器的初始值为0.

 * 还可以在构造方法中带一个 Runnable参数,表示当计数器到达目标值是,在等待CyclicBarrier的线程被唤醒之前,指定该Runnable任务.

 * 2.CyclicBarrier的await方法使当前线程进入等待状态,同时将计数器值加1,当计数器到达目标值时,当前线程被唤醒.

 */

 

public class CyclicBarrierTest {
	public static class ComponentThread implements Runnable {   
        CyclicBarrier barrier;// 计数器   
        int ID;    // 组件标识   
        int[] array;    // 数据数组   
  
        // 构造方法   
        public ComponentThread(CyclicBarrier barrier, int[] array, int ID) {   
            this.barrier = barrier;   
            this.ID = ID;   
            this.array = array;   
        }   
  
        public void run() {   
            try {   
                array[ID] = new Random().nextInt(100);   
                System.out.println("Component " + ID + " generates: " + array[ID]);   
                // 在这里等待Barrier处   
                System.out.println("Component " + ID + " sleep");   
                barrier.await();   
                System.out.println("Component " + ID + " awaked");   
                // 计算数据数组中的当前值和后续值   
                int result = array[ID] + array[ID + 1];   
                System.out.println("Component " + ID + " result: " + result);   
            } catch (Exception ex) {   
            }   
        }   
    }   
    /** *//**  
     * 测试CyclicBarrier的用法  
     */  
    public static void testCyclicBarrier() {   
        final int[] array = new int[3];   
        CyclicBarrier barrier = new CyclicBarrier(2, new Runnable() {   
            // 在所有线程都到达Barrier时执行   
            public void run() {   
                System.out.println("testCyclicBarrier run");   
                array[2] = array[0] + array[1];   
            }   
        });   
  
        // 启动线程   
        new Thread(new ComponentThread(barrier, array, 0)).start();   
        new Thread(new ComponentThread(barrier, array, 1)).start();   
    }   
  
    public static void main(String[] args) {   
        CyclicBarrierTest.testCyclicBarrier();   
    }   

}
