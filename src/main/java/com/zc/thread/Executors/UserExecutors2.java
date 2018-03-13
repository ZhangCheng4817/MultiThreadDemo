package com.zc.thread.Executors;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

// 使用自定义的线程池  -- 使用无界的队列来存储多余的任务
public class UserExecutors2 implements Runnable{

	private static AtomicInteger count = new AtomicInteger(0);
	
	public void run() {
		// TODO Auto-generated method stub
		try {
			System.out.println(count.incrementAndGet());
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static void main(String[] args) throws Exception {
		
 		BlockingQueue<Runnable> queue = 
 				//new LinkedBlockingQueue<Runnable>();// 用于存放线程任务的 无界阻塞队列
 				new ArrayBlockingQueue<Runnable>(10);
 		ExecutorService executorService = new ThreadPoolExecutor(
 				5, 
 				10, 
 				60, 
 				TimeUnit.SECONDS,
 				queue,
 				new MyRjected()
 				);
 		
 		// 循环创建20个任务交给线程池去执行
 		for(int i = 0;i<21;i++){
 			executorService.execute(new UserExecutors2());
 		}
 		
		Thread.sleep(1000);
		System.out.println("线程池中的任务队列大小："+queue.size());
		Thread.sleep(20000);
	}

	
}
