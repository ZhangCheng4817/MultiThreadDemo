package com.zc.thread.Silngle;

// 多线程下的单例模式 -- 静态内部类的实现方式
public class InnerStaticletion {

	private static class Singletion{ // 这就是需要的单例类，作为一个内部的静态类成员
		private static Singletion single = new Singletion(); // 内部类的一个静态实例化的单利对象
	}
	
	// 对外提供一个接口。来获取这个单例对象
	public static Singletion getInstance(){
		return Singletion.single;
	}
}
