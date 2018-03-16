package com.zc.thread.Disruptor.generate1;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.lmax.disruptor.BatchEventProcessor;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.IgnoreExceptionHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SequenceBarrier;
import com.lmax.disruptor.WorkHandler;
import com.lmax.disruptor.WorkerPool;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;

// 使用WorkerPool消息处理器 
public class Main2 {

	
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
		
		
		// 创建 平衡消费者与生产者之间的处理屏障
		SequenceBarrier sequenceBarrier = ringBuffer.newBarrier();
		
		// 创建WorkHandler 事件处理方式
		WorkHandler<Order> workHandler = new OrderHandler();
		
		// 创建线程池
		ExecutorService pool = Executors.newFixedThreadPool(THREAD_SIZE);
		
		// WorkerPool消息处理器
		WorkerPool<Order> workerPool = new WorkerPool<Order>(ringBuffer, sequenceBarrier, new IgnoreExceptionHandler(),workHandler);// 出现异常时，如何处理，可以自己实现接口，自定义处理方式
		
		// 启动这个WorkerPool
		workerPool.start(pool);// 还是需要交给线程池来调度
		
		
		// ------------------生产端--------------------------------
		for(int i = 0;i<20;i++){
			long seq = ringBuffer.next();//获取下一个使用的槽点序列号
			ringBuffer.get(seq).setPrice(Math.random()*9999);
			// 发布事件
			ringBuffer.publish(seq);// 就是说这个序列号上的槽点数据对象已经被赋值生产出来了
		}
		Thread.sleep(1000);
		workerPool.halt();//通知 消息处理器可以结束了
		pool.shutdown();
		
	}
	
}
