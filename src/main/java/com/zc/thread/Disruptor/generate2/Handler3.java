package com.zc.thread.Disruptor.generate2;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;
import com.zc.thread.Disruptor.generate1.Order;

public class Handler3 implements EventHandler<Order>,WorkHandler<Order> {

	public void onEvent(Order order) throws Exception {
		// TODO Auto-generated method stub 
		System.out.println("消费处理者handler3 得到id为"+order.getId()+" order的处理结果：name为"+order.getName()+"，price为"+order.getPrice());
	}

	public void onEvent(Order order, long arg1, boolean arg2) throws Exception {
		// TODO Auto-generated method stub
		this.onEvent(order);
	}

}
