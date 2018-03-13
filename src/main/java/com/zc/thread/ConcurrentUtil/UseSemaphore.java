package com.zc.thread.ConcurrentUtil;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

// 使用信号量
public class UseSemaphore {

	public static void main(String[] args) {
		
		// 创建一个线程池
		ExecutorService pool = Executors.newCachedThreadPool();
		// 创建一个信号量工具类，最多能运行5个线程同时访问
		final Semaphore semp = new Semaphore(5);
		
		// 模拟创建20个线程，去并发的执行
		for(int i = 0;i<20;i++){
			final int num = i;
			Runnable run = new Runnable() {
				
				public void run() {
					// TODO Auto-generated method stub
					// 让当前这个线程试图去获得许可
					try {
						semp.acquire();// 只有获得许可的线程才能被执行，否则一直阻塞着，而这个获得许可，就相当于是一个拿到可以执行的令牌
						System.out.println("Accessing:"+num);
						//Thread.sleep((long)(Math.random()*10000));
						//Thread.sleep(10000*new Random().nextInt());
						semp.release();// 线程执行完毕后，将这个执行的令牌释放掉，给外面等着执行的线程去执行
					
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			};
			
			pool.execute(run);// 将这个线程执行目标类，交个线程池创建线程去执行
		}
		
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		pool.shutdown();
	}
}
