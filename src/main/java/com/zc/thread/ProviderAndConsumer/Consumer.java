package com.zc.thread.ProviderAndConsumer;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

// 消费者
public class Consumer implements Runnable{
	
	private BlockingQueue<Data> queue;// 内存缓冲区，共享的
	
	public void setQueue(BlockingQueue queue){
		this.queue = queue;
	}
	
	public Consumer(BlockingQueue queue){
		setQueue(queue);
	}
	private static Random random = new Random();

	
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			try {
				
				// 获取数据
				Data data  = this.queue.take();// 最后，一定是消费者阻塞在这里，因为没有生产者生产数据了
				// 模拟消费端消费处理数据的耗时
				Thread.sleep(random.nextInt(1000));
				System.out.println("当前消费数据线程："+Thread.currentThread().getName()+"，消费了id为"+data.getId()+"的数据");
				System.out.println("消费数据线程的状态"+Thread.currentThread().getState());
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
}
