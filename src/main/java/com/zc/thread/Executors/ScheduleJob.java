package com.zc.thread.Executors;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

// 带有定时任务的线程池
public class ScheduleJob {

	public static void main(String[] args) {
		
		Temp tem = new Temp();// 线程执行的目标对象
		
		ScheduledExecutorService shExecutorService = Executors.newScheduledThreadPool(1);// 创建带有延迟移除任务的线程池
		// 5：指定5秒后初始化加载这个任务，1：每个1秒中去轮询执行这个tem任务
		ScheduledFuture<?> scheduledFuture = shExecutorService.scheduleWithFixedDelay(tem, 5, 1, TimeUnit.SECONDS);
		
	}
}


class Temp implements Runnable{
	
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("run...");
	}	
}