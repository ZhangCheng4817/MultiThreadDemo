package com.zc.thread.Disruptor.generate1;

import java.util.UUID;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;

public class OrderHandler implements EventHandler<Order> ,WorkHandler<Order> {

	public void onEvent(Order order) throws Exception {
		// TODO Auto-generated method stub
		order.setId(UUID.randomUUID().toString());
		System.out.println("处理 order订单的id为："+order.getId());
	}

	// 真正消费者逻辑
	public void onEvent(Order order, long arg1, boolean arg2) throws Exception {
		// TODO Auto-generated method stub
		this.onEvent(order);
	}

}
