package com.zc.thread.Disruptor.mutil;

import java.util.concurrent.atomic.AtomicInteger;

import com.lmax.disruptor.WorkHandler;
import com.zc.thread.Disruptor.generate1.Order;

public class Consumer implements WorkHandler<Order>{
	
	private String consumerId;
	
	private static AtomicInteger count = new AtomicInteger(0);// 三个消费者共同并发操作的属性
	
	public Consumer(String consumerId){
		this.consumerId = consumerId;
	}

	public void onEvent(Order order) throws Exception {
		System.out.println("当前消费者: " + this.consumerId + "，消费信息：" + order.getId());
		count.incrementAndGet();
	}
	
	public int getCount(){
		return count.get();
	}

}
