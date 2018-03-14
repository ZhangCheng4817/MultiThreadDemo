package com.zc.thread.Disruptor;

import java.nio.ByteBuffer;

import com.lmax.disruptor.RingBuffer;

// 数据的生产者
public class LongEventProducer {

	private RingBuffer<LongEvent> ringBuffer ; // 是引用指向了Disruptor中的那个存数据的容器，生产者 ---> RingBuffer(容器) <--- 消费者 
	
	// 目的是实例化这个生产者的时候，将Disruptor内的数据容器引用赋值给这个生产者去使用，往里面添加数据
	public LongEventProducer(RingBuffer<LongEvent> ringBuffer){
		this.ringBuffer = ringBuffer;
	}
	
	
	// 发布事件 -- 其实就是往容器里面添加数据
	public void addData(ByteBuffer byteBuffer){
		// 1.获取ringBuffer的槽序列号
		long sequence = ringBuffer.next();// 获取下一个槽序列号，就相当于数组的下标索引
		try {
			//2.用上面的索引，取出一个空的事件（槽格），将数据填充进去
			LongEvent longEvent = ringBuffer.get(sequence);// 相当于一个空对象，往里面添加数据即可
			//3.将数据放入到这个槽中
			longEvent.setValue(byteBuffer.getLong(0));
		} finally {
			// TODO: handle finally clause
			// 4.最后一定要发布事件，就是将装有数据的对象再添加回那个缓冲区容器中，提供给消费者使用
			ringBuffer.publish(sequence);
		}
		
	}
	
	
}
