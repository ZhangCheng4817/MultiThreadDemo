package com.zc.thread.Queue;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

// 模拟网吧上网，时间限制下机，加入到DelayQueue延迟性的阻塞队列中，需要实现Delayed接口
public class WangMing implements Delayed{

	
	private String name;// 网名
	
	private int id;// 身份证id
	
	private long endTime;// 截止上网时间的毫秒数
	
	private TimeUnit timeUnit = TimeUnit.SECONDS;// 时间单位
	
	
	public WangMing(String name,int id,long endTime){
		this.name = name;
		this.id = id;
		this.endTime = endTime;
	}
	
	//相互比较排序，判断谁的下机时间更晚，下机时间晚的，就排在队列后面
	public int compareTo(Delayed o) {
		// TODO Auto-generated method stub
		WangMing w = (WangMing)o;
		return this.getDelay(this.timeUnit)-w.getDelay(this.timeUnit) > 0 ? 1:0;
	}

	
	// 是否到了延迟截止的时间
	public long getDelay(TimeUnit unit) { // 如果返回为负数，就移除当前元素
		// TODO Auto-generated method stub
		return this.endTime - System.currentTimeMillis();
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}


}
