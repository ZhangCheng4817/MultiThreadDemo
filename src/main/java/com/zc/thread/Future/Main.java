package com.zc.thread.Future;

public class Main {

	public static void main(String[] args) {
		
		FutureClient fc = new FutureClient();// 创建一个客户端，模拟的发送请求
		Data data = fc.request("id=1");// 这个返回的是futureData的对象引用。也就是代理数据对象
		System.out.println("请求发送成功");
		// 主线程可以去做其他的事
		System.out.println("主线程可以做其他的事");
		String result = data.getRequest();
		System.out.println("请求的结果真实数据为："+result);
	}
}
