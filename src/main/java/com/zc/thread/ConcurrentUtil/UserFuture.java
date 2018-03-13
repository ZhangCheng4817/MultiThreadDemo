package com.zc.thread.ConcurrentUtil;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

// 使用future
public class UserFuture implements Callable<String>{

	private String para;
	
	public UserFuture(String para){
		this.para = para;
	}
	
	// 真实的处理业务逻辑，可能执行时间很长
	public String call() throws Exception {
		// TODO Auto-generated method stub
		Thread.sleep(5000);
		return this.para + " 处理完成！";
	}
	
	public static void main(String[] args) throws Exception{
		String queryStr = "请求参数a";
		
		// 构造一个FutureTask任务，并且需要传入一个真正用于处理业务逻辑的类，该类必须要实现Callable接口，在重载call方法中去执行业务逻辑
		FutureTask<String> future1 = new FutureTask<String>(new UserFuture(queryStr));
		FutureTask<String> future2 = new FutureTask<String>(new UserFuture(queryStr));
		//System.out.println("数据："+future1.get());// 有任务没有执行完，就去调用获取结果的方法，就会一直阻塞在这里
		// 创建一个固定的线程池对象，用于执行这两个future的任务
		ExecutorService pool = Executors.newFixedThreadPool(2);
		
		Future  f1 = pool.submit(future1);// 提交future类的任务去线程池中执行
		System.out.println("数据："+future1.get());// 等到执行完才会有结果，没有结果，则这个get方法就会阻塞等待处理完之后再去获得结果
		Future  f2 = pool.submit(future2);
		System.out.println("请求已发送，等待执行完毕！");
		// 线程池的两个提交任务的方法1.submit 2.execute
		// submit提交的类型参数比execute提交的类型参数要广泛一些
		// submit会返回值，返回的是future类型
		/*while(true){
			if(f1.get() == null){// 返回的future对象调用get方法，如果返回值等于null 表明，这个线程执行future的任务执行完毕
				System.out.println("future1任务执行完毕！");
			}
		}*/
	
		Thread.sleep(1000);
		System.out.println("主线程去执行其他的业务逻辑");
		
		// 调用获取数据的方法，如果call方法没有执行完成。则依然会进行等待
		//System.out.println("数据："+future1.get());
		System.out.println("数据："+future2.get());
		
		pool.shutdown();
		
		
	}
	
}
