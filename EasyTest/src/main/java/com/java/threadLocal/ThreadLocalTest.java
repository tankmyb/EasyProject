package com.java.threadLocal;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadLocalTest implements Runnable{
	private Student s;
	private static ThreadLocal<Student> threadLocal = new ThreadLocal<Student>();
	public ThreadLocalTest(Student s){
		this.s = s;
	}
	@Override
	public void run() {
		String currentThreadName = Thread.currentThread().getName();
        System.out.println(currentThreadName + " is running!");

		Random random = new Random();
        int no = random.nextInt(100);
        s.setName("name"+no);
        System.out.println("thread " + Thread.currentThread().getName() + " first student name :" + s.getName());
        String first = s.getName();
		
		try {
			Thread.sleep(1000L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("thread " +Thread.currentThread().getName()+" second student name:"+s.getName());
		String second = s.getName();
		System.out.println(first.equals(second));
	}
	private Student getStudent(){
		Student s = threadLocal.get();
		if(s==null){
			s = new Student();
			threadLocal.set(s);
		}
		return s;
	}
    public static void main(String[] args) {
    	Student s = new Student();
    	ThreadLocalTest bt = new ThreadLocalTest(s);
    	ExecutorService exec = Executors.newFixedThreadPool(10);
        for (int index = 0; index < 10; index++) {
          exec.execute(new Thread(bt));
        }
        exec.shutdown();
		/*new Thread(bt).start();
		new Thread(bt).start();
		new Thread(bt).start();
		new Thread(bt).start();*/
	}

}
