package com.zc.thread.Disruptor.generate2;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;
import com.zc.thread.Disruptor.generate1.Order;

public class Handler5 implements EventHandler<Order>,WorkHandler<Order> {

	public void onEvent(Order order) throws Exception {
		// TODO Auto-generated method stub
		order.setPrice(order.getPrice()+3);
		System.out.println("消费处理者handler5 set id为："+order.getId()+" order price为："+order.getPrice());
		Thread.sleep(1000);
	}

	public void onEvent(Order order, long arg1, boolean arg2) throws Exception {
		// TODO Auto-generated method stub
		this.onEvent(order);
	}

}
