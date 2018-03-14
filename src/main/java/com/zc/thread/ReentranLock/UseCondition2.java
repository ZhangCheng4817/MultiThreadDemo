package com.zc.thread.ReentranLock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

// 使用多个condition
public class UseCondition2 {

	private ReentrantLock lock = new ReentrantLock();
	
	private Condition condition = lock.newCondition();// 就相当于wait 和notify
	
	private Condition condition2 = lock.newCondition();// 就相当于wait 和notify
	
	public void method1(){
		
		try {
			lock.lock();// 一个线程获得这个锁之后，必须要等这个线程释放锁，才能继续获得
			System.out.println("当前线程："+Thread.currentThread().getName()+"进入执行...");
			//Thread.sleep(3000);
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
				lock.lock();// 一个线程获得这个锁之后，必须要等这个线程释放锁，才能继续获得
				System.out.println("当前线程："+Thread.currentThread().getName()+"进入执行...");
				//Thread.sleep(3000);
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
	
	public void method3(){
		
		try {
			lock.lock();// 一个线程获得这个锁之后，必须要等这个线程释放锁，才能继续获得
			System.out.println("当前线程："+Thread.currentThread().getName()+"进入执行...");
			//Thread.sleep(3000);
			System.out.println("当前线程："+Thread.currentThread().getName()+"进入等待状态并且释放lock锁...");
			condition2.await();// 相当于object wait方法
			System.out.println("当前线程："+Thread.currentThread().getName()+"被唤醒，继续执行");
			
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			System.out.println("当前线程："+Thread.currentThread().getName()+"执行完毕。释放lock锁");
			lock.unlock();
		}
	}
	public void method4(){
		try {
			lock.lock();
			System.out.println("当前线程："+Thread.currentThread().getName()+"进入执行...");
			//Thread.sleep(3000);
			System.out.println("当前线程："+Thread.currentThread().getName()+"唤醒被lock锁释放掉等待的所有线程，让它们继续执行...");
			condition.signalAll();// 相当于object notify方法 唤醒 method1和methond2执行那个线程
			/**
			 * condition.signal()唤醒一个被同一个lock等待的线程
			 * condition.sinalAll() 唤醒所有被同一个lock等待的线程
			 */
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			System.out.println("当前线程："+Thread.currentThread().getName()+"执行完毕。释放lock锁...");
			lock.unlock();
		}
	}
	
	public void method5(){
		try {
			lock.lock();
			System.out.println("当前线程："+Thread.currentThread().getName()+"进入执行...");
			//Thread.sleep(3000);
			System.out.println("当前线程："+Thread.currentThread().getName()+"唤醒另一个被lock锁释放掉等待的线程，让它继续执行...");
			condition2.signal();// 相当于object notify方法 唤醒 method3执行的那个线程
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			System.out.println("当前线程："+Thread.currentThread().getName()+"执行完毕。释放lock锁...");
			lock.unlock();
		}
	}
	 
	public static void main(String[] args) {
		
		final UseCondition2 uc = new UseCondition2();
		
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
		
		Thread t3 = new Thread(new Runnable() {
					
					public void run() {
						// TODO Auto-generated method stub
						uc.method3();
					}
				},"t3");
		
		Thread t4 = new Thread(new Runnable() {
			
			public void run() {
				// TODO Auto-generated method stub
				uc.method4();
			}
		},"t4");
		
		Thread t5 = new Thread(new Runnable() {
			
			public void run() {
				// TODO Auto-generated method stub
				uc.method5();
			}
		},"t5");
		
		t1.start(); 
		t2.start();
		t3.start(); 
		// 延迟2秒再启动t4线程
		try {
			Thread.sleep(2000);
			t4.start(); 
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 再延迟2秒再启动t5线程
		try {
			Thread.sleep(5000);
			t5.start(); 
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
