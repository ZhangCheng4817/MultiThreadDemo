package com.zc.thread.Disruptor;

// disruptor框架并发处理的数据单元类
public class LongEvent {

	private long value;

	public long getValue() {
		return value;
	}

	public void setValue(long value) {
		this.value = value;
	}
	
	
}
