package com.zc.thread.Queue;

// 模拟任务 要放入到PriorityBlockingQueue优先级队列中，该放入的对象要实现Comparable对比的接口
public class Task implements Comparable<Task> {

	private int id;
	private String name;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	// 需要实现一个对比的方法
	public int compareTo(Task task) {
		// TODO Auto-generated method stub
		return this.id>task.id?1:(this.id<task.id?-1:0);
	}
	
	@Override
	public String toString() {
		return "Task [id=" + id + ", name=" + name + "]";
	}

	
	
}
