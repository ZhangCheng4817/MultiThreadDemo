package com.zc.thread.masterWorker;

import java.util.Random;

public class Main {

	public static void main(String[] args) {
		
		// 模拟测试master-worker
		Master master = new Master(new MyWorker2(), 20);// 10个线程worker执行任务   100/10 *0.5 = 5秒
		
		Random random = new Random();
		
		for(int i = 1 ;i<=100;i++){
			Task task = new Task();
			task.setId(i);
			task.setName("任务"+i);
			task.setPrice(random.nextInt(1000));// 在1000内随机获取一个整数作为任务的价格
			master.submit(task);// 提交任务
			
		}
		
		// 提交完任务后，需要启动执行
		master.execute();
		
		long startTime = System.currentTimeMillis();
		// 判断任务线程是否都已经执行完毕
		
		while(true){
			if(master.isComplete()){
				// 如果都已经 完成了，就返回结果
				long endTime = System.currentTimeMillis();
				int result = master.getResult();
				System.out.println("master 调度worker多任务并行计算的结果为："+result+";耗时："+(endTime-startTime)+"秒");
				break;
			}
			
		}
	}
}
