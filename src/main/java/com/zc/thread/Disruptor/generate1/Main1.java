package com.zc.thread.Disruptor.generate1;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.lmax.disruptor.BatchEventProcessor;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SequenceBarrier;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;

// 使用EventProcesser消息处理器  
public class Main1 {

	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		
		int BUFFER_SIZE = 1024;
		int THREAD_SIZE = 4;
		
		// 创建RingBuffer Disruptor缓冲区
		final RingBuffer<Order> ringBuffer = RingBuffer.createSingleProducer(new EventFactory<Order>() {

			// EventFactory 就是创建这个环形的数据结构的槽点对象
			public Order newInstance() {
				// TODO Auto-generated method stub
				
				return new Order();
			}
			
		}, BUFFER_SIZE,new YieldingWaitStrategy());
		
		// 创建线程池
		ExecutorService pool = Executors.newFixedThreadPool(THREAD_SIZE);
		
		// 创建 平衡消费者与生产者之间的处理屏障
		SequenceBarrier sequenceBarrier = ringBuffer.newBarrier();
		
		// ------------------消费端-------------------------------
		// 创建消息处理器(消费者的代理对象)
		BatchEventProcessor<Order> orderProcesser = new BatchEventProcessor<Order>(ringBuffer, sequenceBarrier, new OrderHandler());
		
		// 需要告知生产者，消费者目前在消费哪个槽点索引的数据
		//消息处理器，正在处理哪个位置上的数据，需要告诉生产者。一个消费者可以不写这句，如果有多个消费者，就必须要加上，因为不知道哪个消费者在处理哪个槽点索引位置上的数据
		ringBuffer.addGatingSequences(orderProcesser.getSequence());
		
		// 将消息处理器提交给线程池去处理
		pool.submit(orderProcesser); 
		
		
		// ------------------生产端--------------------------------
		Future<Order> future = pool.submit(new Callable<Order>() {

			public Order call() throws Exception {
				// TODO Auto-generated method stub
				long seq ;
				
				for(int i = 0;i<20;i++){
					seq = ringBuffer.next();//获取下一个使用的槽点序列号
					ringBuffer.get(seq).setPrice(Math.random()*9999);
					// 发布事件
					ringBuffer.publish(seq);// 就是说这个序列号上的槽点数据对象已经被赋值生产出来了
				}
				
				return null;
			}// Callable线程池能够处理的任务类型，必须要实现Runnable接口或者Callable接口
		});
		
		future.get();// 表示等待线程池处理完生产者生产数据的结果。如果没有处理完， 就会阻塞在这里
		Thread.sleep(1000);
		orderProcesser.halt();//通知 消息处理器可以结束了
		pool.shutdown();
		
	}
	
}
