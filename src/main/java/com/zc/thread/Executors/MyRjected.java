package com.zc.thread.Executors;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

// 自定义线程池的拒绝策略
public class MyRjected implements RejectedExecutionHandler{

	// 线程池执行拒绝任务的操作
	public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
		// TODO Auto-generated method stub
		System.out.println("自定义线程池的拒绝策略...");
		System.out.println("当前被拒绝的线程为："+r.toString());
	}

}
