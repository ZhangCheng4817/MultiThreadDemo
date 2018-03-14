package com.zc.thread.ReentranLock;

import java.util.concurrent.locks.ReentrantLock;

// 使用重入锁
public class UseReentranLock {

	private ReentrantLock lock = new ReentrantLock();// 一把锁
	
	public  void method1(){
		try {
			lock.lock();// 进入方法1，就先获得锁，其他线程如果也试图获得lock锁，必须要等待执行method1的线程将该锁释放掉
			System.out.println("当前线程："+Thread.currentThread().getName()+"进入method1方法...");
			Thread.sleep(1000);
			System.out.println("当前线程："+Thread.currentThread().getName()+"退出method1方法...");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			System.out.println("当前线程："+Thread.currentThread().getName()+"释放了lock锁");
			lock.unlock();
		}
	}
	
	public void method2(){
		try {
			lock.lock();// 进入方法1，就先获得锁，其他线程如果也试图获得lock锁，必须要等待执行method1的线程将该锁释放掉
			System.out.println("当前线程："+Thread.currentThread().getName()+"进入method2方法...");
			Thread.sleep(1000);
			System.out.println("当前线程："+Thread.currentThread().getName()+"退出method2方法...");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			System.out.println("当前线程："+Thread.currentThread().getName()+"释放了lock锁");
			lock.unlock();
		}
	}
	
	public static void main(String[] args) {
		
		final UseReentranLock uc = new UseReentranLock();
		
		Thread t1 = new Thread(new Runnable() {
			
			public void run() {
				// TODO Auto-generated method stub
				uc.method1();
				uc.method2();
			}
		},"t1");
		
		t1.start();
	}
}
