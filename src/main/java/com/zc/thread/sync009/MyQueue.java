package com.zc.thread.sync009;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.omg.CORBA.TIMEOUT;

public class MyQueue {
	
	//1 需要一个承装元素的集合 
	private LinkedList<Object> list = new LinkedList<Object>();
	
	//2 需要一个计数器
	private AtomicInteger count = new AtomicInteger(0);
	
	//3 需要制定上限和下限
	private final int minSize = 0;
	
	private final int maxSize ;
	
	//4 构造方法
	public MyQueue(int size){
		this.maxSize = size;
	}
	
	//5 初始化一个对象 用于加锁
	private final Object lock = new Object();
	
	
	//put(anObject): 把anObject加到BlockingQueue里,如果BlockQueue没有空间,则调用此方法的线程被阻断，直到BlockingQueue里面有空间再继续.
	public void put(Object obj){
		synchronized (lock) {
			while(count.get() == this.maxSize){
				try {
					lock.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			//1 加入元素
			list.add(obj);
			//2 计数器累加
			count.incrementAndGet();
			//3 通知另外一个线程（唤醒）
			lock.notify();
			System.out.println("新加入的元素为:" + obj);
		}
	}
	
	
	//take: 取走BlockingQueue里排在首位的对象,若BlockingQueue为空,阻断进入等待状态直到BlockingQueue有新的数据被加入.
	public Object take(){
		Object ret = null;
		synchronized (lock) {
			while(count.get() == this.minSize){
				try {
					lock.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			//1 做移除元素操作
			ret = list.removeFirst();
			//2 计数器递减
			count.decrementAndGet();
			//3 唤醒另外一个线程
			lock.notify();
		}
		return ret;
	}
	
	public int getSize(){
		return this.count.get();
	}
	
	
	public static void main(String[] args) {
		
		final MyQueue mq = new MyQueue(5);// 初始化一个模拟队列的长度为5
		mq.put("1");
		mq.put("2");
		mq.put("3");
		mq.put("4");
		mq.put("5");
		System.out.println("当前容器的长度为:"+mq.getSize());
		Thread t1 = new Thread(new Runnable() {
			
			public void run() {
				// TODO Auto-generated method stub
				mq.put("6");
				mq.put("7");
				
			}
		},"t1");
		
		t1.start();// 先启动t1线程去试图获取元素，由于此时容器已经满了，所以t1线程就会处于阻塞等待的状态
		
		Thread t2 = new Thread(new Runnable() {
			
			public void run() {
				// TODO Auto-generated method stub
				Object o1 = mq.take();
				System.out.println("获取一个元素："+o1);
				Object o2 = mq.take();
				System.out.println("获取一个元素："+o2);
			}
		},"t2");
		
		try {
			TimeUnit.SECONDS.sleep(2);// 让主线程暂停2秒，然后再去启动t2 拿元素
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		t2.start();
	}
	
	
	
}
