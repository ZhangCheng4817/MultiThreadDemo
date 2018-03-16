package com.zc.thread.Disruptor.generate2;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

import com.lmax.disruptor.EventTranslator;
import com.lmax.disruptor.dsl.Disruptor;
import com.zc.thread.Disruptor.generate1.Order;

// 生产者生产数据
public class OrderPublisher implements Runnable {

	private CountDownLatch latch;
	private Disruptor<Order> disruptor;
	
	private static int loop = 1;
	public void run() {
		// TODO Auto-generated method stub
		OrderEventTranslator translator = new OrderEventTranslator();
		for(int i = 0;i<loop;i++){
			disruptor.publishEvent(translator);// 发布者翻译器（造数据）
		}
		
		// 发布完之后，这个线程需要唤醒被CountDownLatch等待的线程
		latch.countDown();
		
	}

	
	public OrderPublisher(CountDownLatch latch,Disruptor<Order> disruptor){
		this.latch = latch;
		this.disruptor = disruptor;
	}

}

class OrderEventTranslator implements EventTranslator<Order>{

	private Random random = new Random();
	public void translateTo(Order order, long arg1) {
		// TODO Auto-generated method stub
		this.generateOrder(order);
	}
	
	
	public Order generateOrder(Order order){
		order.setId(random.nextInt(10)*10+"");
		order.setPrice(random.nextDouble()*9999);
		return order;
	}
}



