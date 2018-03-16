package com.zc.thread.Disruptor.generate2;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;
import com.zc.thread.Disruptor.generate1.Order;

public class Handler4 implements EventHandler<Order> ,WorkHandler<Order> {

	public void onEvent(Order order) throws Exception {
		// TODO Auto-generated method stub
		order.setName(order.getName()+"h4");
		System.out.println("消费处理者handler4 set id为："+order.getId()+" order name为："+order.getName());
		Thread.sleep(1000);
	}

	public void onEvent(Order order, long arg1, boolean arg2) throws Exception {
		// TODO Auto-generated method stub
		this.onEvent(order);
	}

}
