package com.zc.thread.ProviderAndConsumer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

// 测试生产者与消费者模式
public class Main {

	public static void main(String[] args) {
		// 定义一个共享的内存缓冲区
		BlockingQueue<Data> queue = new LinkedBlockingQueue<Data>();
		
		// 创建多个生产者 -- 都是实现了Runnable接口的类，线程类
		Provider p1 = new Provider(queue); 
		Provider p2 = new Provider(queue); 
		Provider p3 = new Provider(queue); 
		
		// 创建多个消费者 -- 都是实现了Runnable接口的类，线程类
		Consumer c1 = new Consumer(queue);
		Consumer c2 = new Consumer(queue);
		Consumer c3 = new Consumer(queue);
		
		// 创建线程池运行，可以创建无穷大的线程，没有任务的时候，不创建线程，空闲线程存活时间为60s
		ExecutorService cachePool = Executors.newCachedThreadPool();// 创建带有内存缓冲区的线程池
		// 线程池根据传入的线程类对象，去创建对应的线程，并且启动执行
		cachePool.execute(p1);
		cachePool.execute(p2);
		cachePool.execute(p3);
		cachePool.execute(c1);
		cachePool.execute(c2);
		cachePool.execute(c3);
		
		// 3秒之后，结束所有的生产者线程
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		p1.stop();
		p2.stop();
		p3.stop();
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
