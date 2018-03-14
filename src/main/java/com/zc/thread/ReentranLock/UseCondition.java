package com.zc.thread.ReentranLock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class UseCondition {

	private ReentrantLock lock = new ReentrantLock();
	
	private Condition condition = lock.newCondition();// 就相当于wait 和notify

	public void method1(){
		
		try {
			lock.lock();// 一个线程获得这个锁之后，必须要等这个线程释放锁，才能继续获得
			System.out.println("当前线程："+Thread.currentThread().getName()+"进入执行...");
			Thread.sleep(3000);
			System.out.println("当前线程："+Thread.currentThread().getName()+"进入等待状态并且释放lock锁...");
			condition.await();// 相当于object wait方法
			System.out.println("当前线程："+Thread.currentThread().getName()+"被唤醒，继续执行");
			
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			System.out.println("当前线程："+Thread.currentThread().getName()+"执行完毕。释放lock锁");
			lock.unlock();
		}
	}
	
	public void method2(){
		try {
			lock.lock();
			System.out.println("当前线程："+Thread.currentThread().getName()+"进入执行...");
			Thread.sleep(3000);
			System.out.println("当前线程："+Thread.currentThread().getName()+"唤醒另一个被lock锁释放掉等待的线程，让它继续执行...");
			condition.signal();// 相当于object notify方法
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			System.out.println("当前线程："+Thread.currentThread().getName()+"执行完毕。释放lock锁...");
			lock.unlock();
		}
	}
	 
	public static void main(String[] args) {
		
		final UseCondition uc = new UseCondition();
		Thread t1 = new Thread(new Runnable() {
			
			public void run() {
				// TODO Auto-generated method stub
				uc.method1();
			}
		},"t1");
		
		Thread t2 = new Thread(new Runnable() {
			
			public void run() {
				// TODO Auto-generated method stub
				uc.method2();
			}
		},"t2");
		
		t1.start(); 
		t2.start();  
	}
}
