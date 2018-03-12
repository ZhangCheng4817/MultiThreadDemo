package com.zc.thread.masterWorker;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

// 负责并行计算的总调度单元类
/*
 * master 负责调度worker --- > worker 负责执行task任务 ，将计算结果返回给master总结
 */
public class Master {

	//1.需要一个承装所有任务的集合 --- 存在多个工作worker去并发的获取任务，所以要保证线程安全
	private ConcurrentLinkedQueue<Task> taskQueue = new ConcurrentLinkedQueue();// 所有的任务放在master的成员变量集合中保存
	
	//2.需要一个承装所有工作的集合 --- 不存在并发操作
	private HashMap<String, Thread> workers = new HashMap<String, Thread>();
	
	//3.需要一个承装每个worker工作执行task任务的结果集    --- 存在多个worker执行任务并发的往这个结果集写入数据
	private ConcurrentHashMap<String,Object> resultMap = new ConcurrentHashMap<String, Object>();
	
	//4.构造函数
	public Master(Worker worker,int workerCount){// 构造这个总调度单元的类的时候，需要指定
		
		// 将任务队列和结果队列赋值给worker
		worker.setTaskQueue(this.taskQueue);
		worker.setResultMap(this.resultMap);
		worker.setSubWorker(worker);// 将处理方式交给自己的子类来实现
		// 将worker加入到master中
		for(int i = 0 ;i<workerCount;i++){
			workers.put("子节点"+Integer.toString(i+1), new Thread(worker));// 使用worker来作为一个线程的执行目标对象
		}
		
	}
	
	
	// 5.提交任务方法
	public void submit(Task task){
		this.taskQueue.add(task);
	}
	
	//6.需要一个执行调度worker的方法
	public void execute(){
		for(Map.Entry<String, Thread> map:workers.entrySet()){
			map.getValue().start();
		}
	}


	// 7.判断任务是否都已经完成
	public boolean isComplete() {
		// TODO Auto-generated method stub
		for(Map.Entry<String, Thread> map:workers.entrySet()){
			if(map.getValue().getState() != Thread.State.TERMINATED){// 只要有一个线程没有停止，就没有完成
				return false;
			}
		}
	
		return true;// 所有的worker线程都已经执行完毕，停止了，也就是，takeQueue里面没有任务了
	}
	
	// 8.汇总每个worker执行完任务的结果
	public int getResult(){
		int result = 0;
		for(Map.Entry<String, Object> map : resultMap.entrySet()){
			result += (Integer)map.getValue();
		}
		
		return result;
	}
	
}
