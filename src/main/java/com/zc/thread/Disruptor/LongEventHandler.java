package com.zc.thread.Disruptor;

import com.lmax.disruptor.EventHandler;

// 事件处理（数据处理）
public class LongEventHandler implements EventHandler<LongEvent>{


	public void onEvent(LongEvent event, long arg1, boolean arg2) throws Exception {
		// TODO Auto-generated method stub
		System.out.println(event.getValue());
	}

}
