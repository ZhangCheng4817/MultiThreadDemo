package com.zc.thread.Queue;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

public class PriorityBlockingQueueDemo {

	//PriorityBlockingQueue是实现了BlockingQueue接口队列的优先级队列
	public static void blockingQueue(final BlockingQueue<PriorityTask> queue) throws Exception{
		
		Random random = new Random();
		for(int i = 0;i<10;i++){
			int priority = random.nextInt(1000);// 获取0-1000内的随机整数
			System.out.println("元素优先级为："+priority);
			// 优先级队列PriorityBlockingQueue ，由于添加入队列的元素需要实现Comparable接口对比方法，根据对比规则排序后，再按照从小到大的顺序添加到队列中
			// 获取元素的时候。从头开始取，因为都是队列，先进先出，小的排在前面
			queue.put(new PriorityTask(priority));// 将任务添加到队列中
			
		}
		
		// 开启一个线程消费队列中的任务
		new Thread(new Runnable() {
			
			public void run() {
				// TODO Auto-generated method stub
				while(!Thread.currentThread().isInterrupted()){
					// 从队列中获取任务
					try {
						queue.take().run();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
	
	public static void main(String[] args) throws Exception {
		//System.out.println("测试linkedBlockingQueue普通阻塞队列");
		//blockingQueue(new LinkedBlockingQueue<PriorityTask>());// 按照队列中的任务加入时的顺序依次执行
		System.out.println("测试PriorityBlockingQueue优先级阻塞队列");
		blockingQueue(new PriorityBlockingQueue<PriorityTask>());
	}
}
