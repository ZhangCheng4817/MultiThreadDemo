package com.zc.thread.threadLocal;

// 当前线程
public class ThreadLocalDemo1 {

	public static ThreadLocal<String> th = new ThreadLocal<String>();
	
	public void setTh(String value){
		this.th.set(value);
	}
	
	public void getTh(){
		System.out.println(Thread.currentThread().getName()+":"+this.th.get());
	}
	
	public static void main(String[] args) {
		
		final ThreadLocalDemo1 td = new ThreadLocalDemo1();
		
		Thread t1 = new Thread(new Runnable() {
			
			public void run() {
				// TODO Auto-generated method stub
				td.setTh("张三");
				td.getTh();
			}
		},"t1");
		
		Thread t2 = new Thread(new Runnable() {
			
			public void run() {
				// TODO Auto-generated method stub
				try {
					Thread.sleep(2000);
					td.getTh();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		},"t2");
		
		t1.start();
		t2.start();
		/**
		 * 结果分析：
		 * t1:张三
		   t2:null
		   ThreadLocal 是针对每个线程个体特有的存储对象，t1自己给自己的ThreadLocal容器放入了值
		   t2线程没有给字节的ThreadLocal设置值。也就是为空
		 */
	}
	
	
}
