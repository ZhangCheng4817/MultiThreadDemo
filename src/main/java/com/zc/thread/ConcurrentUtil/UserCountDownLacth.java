package com.zc.thread.ConcurrentUtil;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

// CountDownLacth：经常用于监听某个线程的初始化操作是否完成。如果完成，就可以通知另一个等待的线程去执行相关的操作
/*
 * 比如说，需要连接zookeeper集群，必须要先连接上zookeeper集群，获取客户端对象，然后才能利用这个客户端对象去执行相关的操作
 * 就可以使用CountDownLacth，在连接zookeeper集群的时候，当连接成功时，唤醒另一个等待使用这个客户端的线程，才能保证顺利运行，否则，客户端都没有连接成功
 * 另一个使用客户端的线程直接去执行操作，就会出现错误
 */
public class UserCountDownLacth {
	
	
	
	public static void main(String[] args) {
		
		final CountDownLatch countDownLatch = new CountDownLatch(1);// 这个参数是指明需要调用几次countDown方法才能去唤醒另一个等待的线程
		
		Thread t1 = new Thread(new Runnable() {
			
			public void run() {
				// TODO Auto-generated method stub
				try {
					System.out.println("进入线程t1等待其他线程执行完毕...");
					countDownLatch.await();
					System.out.println("线程t1被唤醒，继续执行...");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		},"t1");
		
		Thread t2 = new Thread(new Runnable() {
			
			public void run() {
				// TODO Auto-generated method stub
				try {
					System.out.println("进入线程t2，进行初始化操作...");
					Thread.sleep(3000);
					System.out.println("线程t2执行完毕...唤醒t1线程去继续执行");
					countDownLatch.countDown();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		},"t2");
		
		Thread t3 = new Thread(new Runnable() {
			
			public void run() {
				// TODO Auto-generated method stub
				try {
					System.out.println("进入线程t3，进行初始化操作...");
					Thread.sleep(4000);
					System.out.println("线程t3执行完毕...唤醒t1线程去继续执行");
					countDownLatch.countDown();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		},"t3");
		
		
		t1.start();
		t2.start();
		t3.start();
		
	}
}
