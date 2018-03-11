package com.zc.thread.Queue;

// 优先级任务
public class PriorityTask implements Runnable,Comparable{

	private int priority;
	
	public PriorityTask(int priority){
		this.priority = priority;
	}
	// 对比规则
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		PriorityTask task = (PriorityTask)o;
		if(this.priority == task.priority){
			return 0;
		}
		return this.priority>task.priority?1:-1;
	}

	// 线程执行方法
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("优先级为"+this.priority+"的任务执行完毕！");
	}

}
