package com.zc.thread.Executors;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

// 使用自定义的线程池 -- 使用有界队列来存储多余的任务
public class UserExecutors1 {

	public static void main(String[] args) {
		
		/**
		 * 在使用有界队列，来存储任务时，若有新的任务需要执行，如果线程池中的实际线程数小于corePoolSize，则优先创建线程来执行任务
		 * 若大于corePoolSize，则会将任务放入到有界队列中去等待线程执行
		 * 若队列也满了，则在总线程数不大于maxPoolSize的时候，创建新的线程去执行
		 * 若需要的线程数大于maxPoolSize，则会执行拒绝策略，或使用其他的自定义方式
		 * 只有在队列满的情况下，才会去创建新的线程，但是如果线程池中在运行的线程数超过了最大的线程数，就会报出拒绝策略
		 */
		
		ThreadPoolExecutor pool =  new ThreadPoolExecutor(
				1, // 线程池中正在执行的线程数 corePoolSize
				2,// 线程池最大能够创建的线程数 maximumPoolSize
				60,// 线程的最大空闲时间，一旦超过这个空闲的时间，那么该线程就会被内存回收，销毁
				TimeUnit.SECONDS, // 时间单位
				new ArrayBlockingQueue<Runnable>(3)// 存放任务的队列，加入使用的是有界的阻塞队列
				//handler 线程池实现拒绝策略
				); 
		
		MyTask task1 = new MyTask(1, "任务1");
		MyTask task2 = new MyTask(2, "任务2");
		MyTask task3 = new MyTask(3, "任务3");
		MyTask task4 = new MyTask(4, "任务4");
		MyTask task5 = new MyTask(5, "任务5");
		MyTask task6 = new MyTask(6, "任务6");
		
		pool.execute(task1);
		pool.execute(task2); // 由于线程池中初始化的线程只有1个，那么，新进来的task2就会被放入到有界阻塞队列中，等待前面的任务执行完毕，然后再执行
		pool.execute(task3); // 放入到有界的阻塞队列中
		pool.execute(task4); // 放入到有界的阻塞队列中
		pool.execute(task5); // 由于队列中已经存满了任务，但是，线程池中线程只有1个，最大容量是2个线程，此时，线程池就会创建一个线程，那么这个线程池就处于饱和状态
							 // 然后就会先执行这个最后添加的任务，在队列中的任务，按照队列的先进先出的原则，依次被取出来，被线程池中的空闲线程去执行，也就是两个
		pool.execute(task6); // 如果线程池处于饱和状态，再继续添加新的任务的话，就会被拒绝处理
		
		pool.shutdown();// 关闭线程池，必须是在线程池中所有的任务都执行完毕后，线程池才会被关闭
		
	}
}
