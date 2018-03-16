package com.zc.thread.Disruptor.mutil;

import java.nio.ByteBuffer;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.ExceptionHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SequenceBarrier;
import com.lmax.disruptor.WorkHandler;
import com.lmax.disruptor.WorkerPool;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.ProducerType;
import com.zc.thread.Disruptor.generate1.Order;

public class Main {
	
	public static void main(String[] args) throws Exception {

		//创建ringBuffer
		RingBuffer<Order> ringBuffer = 
				RingBuffer.create(ProducerType.MULTI,  // 创建多个生产者的ringbuffer
						new EventFactory<Order>() {  
				            public Order newInstance() {  
				                return new Order();  
				            }  
				        }, 
				        1024 * 1024, 
						new YieldingWaitStrategy());
		
		// 平衡生产者与消费者之间数据处理速度的屏障
		SequenceBarrier barriers = ringBuffer.newBarrier();
		
		Consumer[] consumers = new Consumer[3];
		for(int i = 0; i < consumers.length; i++){
			consumers[i] = new Consumer("c" + i);
		}
		
		WorkerPool<Order> workerPool = 
				new WorkerPool<Order>(ringBuffer, 
						barriers, 
						new IntEventExceptionHandler(),
						consumers);// 可变参数。可以传递多个消费者数组
		
		// 有多个消费者，要将消费者正在消费处理的那个数据的槽点索引位置告知给ringbuffer去协调生产者生产数据
        ringBuffer.addGatingSequences(workerPool.getWorkerSequences());  
        // 启动workPool
        workerPool.start(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()));  
        
        final CountDownLatch latch = new CountDownLatch(1);
        for (int i = 0; i < 100; i++) {  
        	final Producer p = new Producer(ringBuffer);// 创建了100个生产者
        	new Thread(new Runnable() { // 必须要等一个线程生产完数据，下一个线程才能进来
				public void run() {
					try {
						latch.await();// 所有线程都阻塞在这里
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					for(int j = 0; j < 100; j ++){
						p.onData(UUID.randomUUID().toString());
					}
				}
			}).start();
        } 
        Thread.sleep(2000);
        System.out.println("---------------开始生产-----------------");
        latch.countDown();// 唤醒所有处于阻塞等待的生产者线程，一旦生产者生产完数据，消费者立马消费处理了
        Thread.sleep(5000);
        System.out.println("总数:" + consumers[0].getCount() );
	}
	
	static class IntEventExceptionHandler implements ExceptionHandler {  
	    public void handleEventException(Throwable ex, long sequence, Object event) {}  
	    public void handleOnStartException(Throwable ex) {}  
	    public void handleOnShutdownException(Throwable ex) {}  
	} 
}
