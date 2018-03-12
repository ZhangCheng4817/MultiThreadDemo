package com.zc.thread.ProviderAndConsumer;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

// 生产者
public class Provider implements Runnable{

	// 获取共享的内存缓冲区的引用
	private BlockingQueue<Data> queue;
	
	// 判断多线程是否启动运行，返回线程运行的状态
	public volatile boolean isRunning = true;
	
	// id生成器
	private static AtomicInteger count = new AtomicInteger();// 共享的变量，多线程之间共享着一个变量，具有原子性，不管多少个线程对它的值进行操作，始终保持正确的结果
	
	// 随机对象
	private static Random random = new Random();
	
	public void setQueue(BlockingQueue queue){
		this.queue = queue;
	}

	public Provider(BlockingQueue queue){
		setQueue(queue);
	}
	
	public void run() {
		// TODO Auto-generated method stub
	
		while(isRunning){
			try {
				// 随机休眠0-1000毫秒，用于模拟生产者生产数据的耗时
//				TimeUnit.SECONDS.sleep(2);
				Thread.sleep(random.nextInt(1000));
				
				// 生产数据
				int id = count.incrementAndGet();
				Data data = new Data();
				data.setId(Integer.toString(id));
				data.setName("数据"+id);
				
				// 添加到这个阻塞队列中
				boolean addResult = queue.offer(data, 2, TimeUnit.SECONDS);// 在隔2秒后添加数据到队列中，返回的是添加的结果状态
				if(!addResult){// 添加失败
					System.out.println("提交数据到阻塞队列内存缓冲区失败！");
				}else{// 添加成功
					System.out.println("当前生产数据线程："+Thread.currentThread().getName()+",生产了id为"+data.getId()+"数据，装载进了内存缓冲区中");
				}
				
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
	
	// 停止线程的方法 线程中的run方法执行完毕后，这个线程就意味着结束
	public void stop(){
		this.isRunning = false;
	}
	
}
