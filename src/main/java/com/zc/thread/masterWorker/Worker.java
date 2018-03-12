package com.zc.thread.masterWorker;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

// 执行任务的工作单元类  --- 线程
public class Worker implements Runnable{

	private ConcurrentLinkedQueue<Task> taskQueue;
	private ConcurrentHashMap<String,Object> resultMap;
	private Worker subWorker; // 具体继承该worker类的子类
	// worker线程执行任务
	public void run() {
		// TODO Auto-generated method stub
		// 要先获取任务
		while(true){
			Task input = this.taskQueue.poll();
			if(input == null) break;// 没有执行的任务
			// 下面就是真正执行任务的操作
			Object output = handle(input);
			// 需要将任务处理完的结果放入到resultMap
			this.resultMap.put(Integer.toString(input.getId()), output);// key 是任务的id value是 执行的结果
		}
		
	}

	// 处理任务的方法
	public Object handle(Task input){
		
		return subWorker.handle(input);
	}
	
	public void setSubWorker(Worker worker){// 设置自己的子类
		this.subWorker  = worker;
	}
	
	public void setTaskQueue(ConcurrentLinkedQueue taskQueue) {
		// TODO Auto-generated method stub
		this.taskQueue = taskQueue;
	}

	public void setResultMap(ConcurrentHashMap<String, Object> resultMap) {
		// TODO Auto-generated method stub
		this.resultMap = resultMap;
	}

}
