package com.zc.thread.ConcurrentUtil;

import java.util.Random;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// 线程一起阻塞，等到达到CyclicBarrier初始化的数量后，多个等待的线程并发的去执行
// 只要有一个线程没有调用await方法，其他阻塞的线程就会一直处于等待的状态，这个取决于初始化CyclicBarrier的数量必须要等于线程调用await的方法的次数
public class UserCyclicBarrier {

	
	static class Runner implements Runnable{
		
		private CyclicBarrier barrier;
		private String name;
		public Runner(CyclicBarrier barrier,String name){
			this.barrier = barrier;
			this.name = name;
		}
		
		public void run() {
			// TODO Auto-generated method stub
			try {
				Thread.sleep(1000*new Random().nextInt(10));// 每个线程随机的睡眠多少秒
				System.out.println(name+"已经准备就绪！");
				barrier.await();// 线程处于阻塞的状态
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println(name+" GO！");// 线程继续执行
		}
		
	}
	
	public static void main(String[] args) {
		
		CyclicBarrier barrier = new CyclicBarrier(3);
		
		// 需要三个线程同时调用这个barrier.await()方法，那么第三个线程一旦调用了barrier.await()方法后，这三个线程就会被同时启动执行
		ExecutorService pool = Executors.newFixedThreadPool(3);// 初始化固定线程数量的线程池对象
		
		pool.execute(new Runner(barrier, "李四")); // 线程中必须要使用同一个CyclicBarrier对象，否则，不会出现一起被唤醒执行的效果
		pool.execute(new Runner(barrier, "王五"));
		pool.execute(new Runner(barrier, "张三"));
		
		pool.shutdown();
	}
}
