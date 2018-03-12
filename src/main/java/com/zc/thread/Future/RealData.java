package com.zc.thread.Future;

// 模拟future模式下的真实数据
public class RealData  implements Data{

	private String result;// 真实的查询结果数据
	

	// 获取查询的真实数据
	public String getRequest() {
		// TODO Auto-generated method stub
		return result;
	}
	
	// 根据查询条件，去获取真实的查询结果数据
	public RealData (String queryStr){
		System.out.println("根据查询数据："+queryStr+"进行查询，这是一个很耗时的操作！");
		// 模拟查询真实数据的耗时
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("查询真实数据完毕，得到结果");
		this.result = "123";
		
	}
	

}
