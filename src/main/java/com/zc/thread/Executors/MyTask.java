package com.zc.thread.Executors;

// 模拟任务
public class MyTask implements Runnable{

	private int taskId;
	private String taskName;
	
	public MyTask(int taskId,String taskName){
		this.taskId = taskId;
		this.taskName = taskName;
	}
	public int getTaskId() {
		return taskId;
	}
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	
	
	public void run() {
		// TODO Auto-generated method stub
		try {
			System.out.println("run taskId = "+this.taskId+"线程开始执行！");
			Thread.sleep(5*1000);
			System.out.println("run taskId = "+this.taskId+"线程执行完毕！");
			System.out.println();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	
}
