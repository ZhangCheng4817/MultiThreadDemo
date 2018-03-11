package com.zc.thread.Queue;

import java.util.concurrent.DelayQueue;

// 模拟网吧
public class WangBa implements Runnable{

	private DelayQueue<WangMing> queue = new DelayQueue<WangMing>();
	
	public boolean yinye = true;// 上机：true 下机：false
	
	// 上机，就需要将这个网民加入到延迟队列中
	public void shangji(String name,int id,int money){
		// 假设一块钱上一秒钟
		WangMing wangming = new WangMing(name, id, 1000*money+System.currentTimeMillis());
		System.out.println("网名为："+wangming.getName()+",身份证为："+wangming.getId()+",交了"+money+"块钱，开始上机！");
		this.queue.add(wangming);// 加入到延迟队列中
	}
	
	// 下机
	public void xiaji(WangMing wangming){
		System.out.println("网名为："+wangming.getName()+",身份证为："+wangming.getId()+"时间到下机了!");
	}
	
	
	public void run() {
		// TODO Auto-generated method stub
		while(yinye){
			try {
				System.out.println("检查ing");  
				WangMing w = queue.take();
				xiaji(w);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		
		System.out.println("网吧开始营业");
		WangBa wangba = new WangBa();
		wangba.shangji("张三", 123, 10);// 10秒
		wangba.shangji("张三", 123, 10);// 10秒
		wangba.shangji("李四", 456, 5);// 5秒
		wangba.shangji("王二", 789, 1);// 3秒
		wangba.queue.take();// 要调用take方法，获取延迟时间最短的那个元素
		Thread shangwang = new Thread(wangba);// shangwang这个线程的目标对象是WangBa类对象，执行的是WangBa类对象中的run方法
		shangwang.start();
		
		
	}

}
