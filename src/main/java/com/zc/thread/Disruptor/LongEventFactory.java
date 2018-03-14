package com.zc.thread.Disruptor;

import com.lmax.disruptor.EventFactory;

// 批量的产生Disroptor框架操作的数据
public class LongEventFactory implements EventFactory {

	
	public Object newInstance() {
		// TODO Auto-generated method stub
		return new LongEvent();
	}

}
