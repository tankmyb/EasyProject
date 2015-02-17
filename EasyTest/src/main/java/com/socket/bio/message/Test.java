package com.socket.bio.message;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Test {

	public static void main(String[] arg) throws InterruptedException, ExecutionException {
		HandlerThread  t1 = HandlerThread.getInstance();
		t1.sendMessage("sdsdsdsdsdsdsdsdsd", null, "aa");
		/*CallableThread t2 = new CallableThread();
		FutureTask<String> primeTask = new FutureTask(t2);
		new Thread(primeTask).start();
		t1.sendMessage("sdsdsdsdsdsdsdsdsd", t2, "aa");
		System.out.println(primeTask.get()+"============");*/
	}

}
