package com.zc.thread.Disruptor;

import java.nio.ByteBuffer;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

public class LongEventMain {

	/**
	 * 在Disruptor并发框架中，需要实现下面五个基本的步骤
	 * 1.建立一个Event类，就是框架操作的数据单元， --- javaBean
	 * 2.建立一个Event工厂类，用于批量的创建Event数据单元类
	 * 3.需要一个事件监听类，用于处理这个Event类数据
	 * 4.需要进行测试代码编写，实例化Disruptor，配置一系列的参数，然后对Disruptor实例绑定事件监听类，并且接受数据并处理数据
	 * 5.在Disruptor中，真正用于存储数据的是RingBuffer 环形的缓冲区数据结构，通过Disruptor实例，来获取这个缓冲区
	 * 把数据生产出来，放入到这个缓冲区中，然后将数据推送给消费者去使用
	 * 
	 */
	public static void main(String[] args) {
		
		// 创建线程缓冲池
		ExecutorService pool = Executors.newCachedThreadPool();
		
		// 创建批量生产操作的数据工厂
		LongEventFactory factory = new LongEventFactory();
		
		// 创建缓冲区的大小
		int ringBufferSize = 1024 * 1024;
		
		
		// 创建Disruptor实例对象
		/*
		 * 四个参数
		 * ProducerType.SINGLE  一个生产者
		 * ProducerType.MULTI 多个生产者
		 * 
		 * new YieldingWaitStrategy()：是对生产和消费两者之间，进行一个协调
		 */
		Disruptor<LongEvent> disruptor = 
				new Disruptor<LongEvent>(factory, ringBufferSize, pool, ProducerType.MULTI, new YieldingWaitStrategy());
		
		// 连接消费者事件方法
		disruptor.handleEventsWith(new LongEventHandler());// 将disruptor生产的数据，推送给消费者进行处理
		
		disruptor.start();// 启动Disruptor
		
		// 发布事件 --- 其实就是将数据放入到Disruptor中，等待消费者去获取、处理
		
		// 先获取到缓冲区内存(容器)
		RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();
		// 需要一个生产者，将生产的数据，交给这个ringBuffer
		LongEventProducer producer = new LongEventProducer(ringBuffer);
		
		// 承装数据搬运车的缓冲区
		ByteBuffer buffer = ByteBuffer.allocate(8);// 就相当于是一个8个长度数组
		for(int i = 0;i<100;i++){
			buffer.putLong(0, i);// 在0位置上放入数据，然后下一个又将0位置上的数据覆盖，再加入到ringBuffer中
			producer.addData(buffer);
		}
		
		disruptor.shutdown();// 关闭Disruptor
		pool.shutdown();// 关闭线程池缓冲区
		
	}
	
}
