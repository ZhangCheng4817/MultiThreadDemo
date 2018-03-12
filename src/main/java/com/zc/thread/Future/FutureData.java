package com.zc.thread.Future;

// 模拟future模式，客户端响应回的“假”数据
public class FutureData implements Data{

	private RealData realData;
	private boolean isReady = false;
	
	// 获取查询结果数据
	public synchronized String getRequest() {
		// TODO Auto-generated method stub
		
		// 如果没有装载数据，这个获取真实数据的那个线程就需要一直处于等待的状态
		while(!isReady){
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// 如果装载过了 ，就直接获取真实数据返回即可
		return this.realData.getRequest();
	}

	// 设置真实数据
	public synchronized void setRealData(RealData realData) {
		// TODO Auto-generated method stub
		if(isReady){// 是否已经装载过了真实的数据
			return;
		}
		
		// 如果没有装载，必须要装载了数据，才能获取真实数据，否则获取真实数据的方法，必须要一直处于阻塞等待的状态
		this.realData = realData;
		isReady = true;
		notify();
		
	}
	

}
