package com.zc.thread.Disruptor.generate2;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.lmax.disruptor.BusySpinWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.EventHandlerGroup;
import com.lmax.disruptor.dsl.ProducerType;
import com.zc.thread.Disruptor.generate1.Order;

// 多消费者之间的执行关系
public class Main {

	public static void main(String[] args) throws Exception{
		
		long bengTime = System.currentTimeMillis();
		
		int buffer_size = 1024;
		
		// 创建线程池
		ExecutorService pool = Executors.newFixedThreadPool(8);
		
		// 实例化创建Distuptor
		@SuppressWarnings({ "unused", "deprecation" })
		Disruptor<Order> disruptor = new Disruptor<Order>(new EventFactory<Order>() {

			public Order newInstance() {
				// TODO Auto-generated method stub
				return new Order();// 创建槽点对象
			}
			
		}, buffer_size, pool,ProducerType.SINGLE,new BusySpinWaitStrategy());
		
		// ***********************消费者****************************
		// 菱形操作
		// 创建处理消费者组（多个消费处理者）
		// 加入EventHandlerGroup组中的那些消费处理者，之间的是并行操作的
		//EventHandlerGroup<Order> handleGroup = disruptor.handleEventsWith(new Handler1(),new Handler2());
		// 在handler1和handler2并行执行完结果后，将处理的同一个order传递给handler3进行处理
		// 这种应用从场景有：互联网金融相关的操作，handler1进行校验，handler2进行日志存储，handler3进行交易，前面两个操作正确执行完，handler3才会获得结果来执行
		//handleGroup.then(new Handler3());// handler1和handler2执行完后，handler3才会执行
		
		// 六边形操作
		/*Handler1 h1 = new Handler1();
		Handler2 h2 = new Handler2();
		Handler3 h3 = new Handler3();
		Handler4 h4 = new Handler4();
		Handler5 h5 = new Handler5();
		disruptor.handleEventsWith(h1,h2);// h1,h2并行执行
		disruptor.after(h1).handleEventsWith(h4);// h1执行完后h4执行
		disruptor.after(h2).handleEventsWith(h5);// h2执行完后h5执行
		disruptor.after(h4,h5).handleEventsWith(h3);// h4和h5并行执行完后，最后将结果传递给h3来执行处理	
		*/
		
		// 顺序执行
		Handler1 h1 = new Handler1();
		Handler2 h2 = new Handler2();
		Handler3 h3 = new Handler3();
		// h1 ---> h2 ---> h3
		disruptor.handleEventsWith(h1).handleEventsWith(h2).handleEventsWith(h3);
		// 启动Disruptor
		disruptor.start();
		
		// *********************生产者********************************
		CountDownLatch latch = new CountDownLatch(1);
		// 生产者
		pool.submit(new OrderPublisher(latch,disruptor));
		
		// 必须等待生产端生产完数据，才能继续后续执行
		latch.await();
		Thread.sleep(1000);
		disruptor.shutdown();
		pool.shutdown();
		
		System.out.println("总耗时："+(System.currentTimeMillis()-bengTime));
		
	}
}
