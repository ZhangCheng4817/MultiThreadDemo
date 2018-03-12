package com.zc.thread.masterWorker;

// 实现多方式的处理
public class MyWorker extends Worker{

	public Object handle(Task input){
		System.out.println("MyWorker 执行");
		Object output = null;
		try {
			// 模拟worker计算任务的花费时间
			Thread.sleep(500);
			output = input.getPrice();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return output;
	}
}
