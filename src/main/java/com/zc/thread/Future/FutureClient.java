package com.zc.thread.Future;

// 模拟一个future 客户端
public class FutureClient {

	// 发送请求的方法
	public Data request(final String queryStr){
		
		// 在线程中使用的变量都需要加上final
		final FutureData futureData = new FutureData();
		
		// 启动一个线程，去加载真实的数据，传递给这个FutureData代理对象，也即是假数据
		new Thread(new Runnable() {
			
			public void run() {
				// TODO Auto-generated method stub
				// 这个新线程可以慢慢的加载真实对象，然后将这个真实数据传递给代理对象
				RealData realData = new RealData(queryStr);// 根据查询条件，开启线程查询真实的数据
				futureData.setRealData(realData);
			}
		}).start();// 线程执行完之后，返回代理数据（装载了真实数据）
		
		return futureData;
	}
	
}
