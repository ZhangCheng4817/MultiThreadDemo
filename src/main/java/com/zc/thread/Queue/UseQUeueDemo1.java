package com.zc.thread.Queue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;


public class UseQUeueDemo1 {

	public static void main(String[] args) throws Exception{
		
		System.out.println("=====================ConcurrentLinkedQueue=========================");
		// 高性能的无阻塞无界队列
		ConcurrentLinkedQueue<String> q = new ConcurrentLinkedQueue<String>();
		q.add("1");
		q.offer("2");
		q.add("3");
		System.out.println(q);
		System.out.println(q.poll());// 移除首位的元素，容器中的首位元素会被删除
		System.out.println(q);
		System.out.println(q.size());
		System.out.println(q.peek());// 获取首位的元素，但是不会删除容器中的元素
		System.out.println(q);
		System.out.println("=====================ArrayBlockingQueue=========================");
		
		// 阻塞队列ArrayBlockingQueue
		ArrayBlockingQueue<String> array = new ArrayBlockingQueue<String>(5);// 声明5个长度大小定长阻塞队列数组
		array.add("a");
		array.add("b");
		array.add("c");
		array.add("d");
		array.add("e");
		//array.add("e");
		System.out.println(array);
		// 第三个参数是时间单位。停顿两秒后，在执行添加元素操作，返回是否添加成功的布尔类型
		//boolean result = array.offer("f", 2, TimeUnit.SECONDS);
		//System.out.println(result);
		
		System.out.println("=====================LinkedBlockingQueue=========================");
		// 阻塞队列LinkedBlockingQueue 无界队列（前提是初始化该队列时，不能声明长度，如果声明长度，就是有界队列）
		// 如果初始化LinkedBlockingQueue 队列的时候，
 		LinkedBlockingQueue<String> linked = new LinkedBlockingQueue<String>(6);
 		linked.add("1");
 		linked.add("2");
 		linked.offer("3");
 		linked.add("4");
 		linked.add("5");
 		linked.add("6");
 		System.out.println(linked);
 		// 取队列中的元素
 		List<String> list = new ArrayList<String>();
 		System.out.println(linked.drainTo(list, 2));// 从队列linked中去前2个元素{1,2}放入到指定的集合list中，返回的是取了几个队列元素
 		
 		for(String str:list){
 			System.out.println(str);
 		}
 		
 		// 具有优先级的阻塞队列PriorityBlockingQueue
 		PriorityBlockingQueue<Task> p = new PriorityBlockingQueue<Task>();
 		Task t1 = new Task();
 		t1.setId(1);
 		t1.setName("任务1");
 		Task t2 = new Task();
 		t2.setId(2);
 		t2.setName("任务2");
 		Task t3 = new Task();
 		t3.setId(6);
 		t3.setName("任务3");
 		
 		p.put(t1);
 		p.put(t3);
 		p.put(t2);
 		
 		System.out.println(p);
 		
 		// SynchronousQueue 同步类的阻塞容器，add方法与take方法需要同时调用
 		final SynchronousQueue<String> sq = new SynchronousQueue<String>();
 		
 		Thread thread1 = new Thread(new Runnable() {
			
			public void run() {
				// TODO Auto-generated method stub
				try {
					// 必须要让take方法先执行，调用take方法的线程必须要处于等待阻塞的状态，一旦另一个线程调用add方法，此时take方法才能取到值
					System.out.println("线程thread1从SynchronousQueue队列中获取一个元素："+sq.take()); 
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
 		
 		thread1.start();// take方法在的线程必须要先执行
 		
 		Thread.currentThread().sleep(1000);
 		
 		Thread thread2 = new Thread(new Runnable() {
			
			public void run() {
				// TODO Auto-generated method stub
				try {
					// 必须要让take方法先执行，调用take方法的线程必须要处于等待阻塞的状态，一旦另一个线程调用add方法，此时take方法才能取到值
					System.out.println("线程thread2从SynchronousQueue队列中添加了一个元素aaa");
					sq.add("aaa");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
 		thread2.start();
 		
	}
}
