package com.zc.thread.Silngle;

// 双重if判断，来创建单例模式
public class DubboCheckSingletion {

	private static DubboCheckSingletion ds ;
	
	public static DubboCheckSingletion getInstance(){
		
		if(ds == null){// 第一重if判断
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			synchronized(DubboCheckSingletion.class){// 加了一个类锁
				//ds = new DubboCheckSingletion(); // 不加第二重if判断的情况
				
				if(ds == null){
					ds = new DubboCheckSingletion();
				}
			}
			
		}
		return ds;
	}
	
	public static void main(String[] args) {
		
		Thread t1 = new Thread(new Runnable() {
			
			public void run() {
				// TODO Auto-generated method stub
				System.out.println("当前线程："+Thread.currentThread().getName()
						+"获取到的单例对象为："+DubboCheckSingletion.getInstance().hashCode());
			}
		},"t1");
		
		Thread t2 = new Thread(new Runnable() {
			
			public void run() {
				// TODO Auto-generated method stub
				System.out.println("当前线程："+Thread.currentThread().getName()
						+"获取到的单例对象为："+DubboCheckSingletion.getInstance().hashCode());
			}
		},"t2");
		
		Thread t3 = new Thread(new Runnable() {
			
			public void run() {
				// TODO Auto-generated method stub
				System.out.println("当前线程："+Thread.currentThread().getName()
						+"获取到的单例对象为："+DubboCheckSingletion.getInstance().hashCode());
			}
		},"t3");
		
		t1.start();
		t2.start();
		t3.start();
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("最终单例对象ds为："+DubboCheckSingletion.ds.hashCode());// 一定与前面三个最后创建的那个ds对象相同，eclipse打印的时间可能存在间隔
		/**
		 *结果分析：
		 *1.不加第二重if判断，不管有几个线程试图去获取这个单例对象，每个对象获取的都是不同的单例对象
		 *因为第一重if判断 是 ds == null 几个线程第一次进入if 的时候，还没有线程创建出单例对象ds
		 *然后逐个进入同步代码块，都去创建了对象 ，前面创建的对象ds 都会被后面创建的对象所覆盖
		 */
	}
}
