package com.zc.thread.concurrentHashMap;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// 并发类容器：concurrentHashMap是采取分段读写的思想
// 就是将一个整体的concurrentHashMap 分成多个段。每个段可以看成是一个小的hashMap
// 读写的时候，先确定段 ，然后在指定的段内进行读写操作
public class ConcurrentHashMapDemo {

	public static void main(String[] args) {
		ConcurrentHashMap<String, Object> chm = new ConcurrentHashMap<String, Object>();
		chm.put("key1", "v1");
		chm.put("key2", "v2");
		chm.put("key3", "v3");
		chm.putIfAbsent("key3", "vvvv");// 这个方法是说如果key3这个键值在容器中存在，就不往里面添加了，如果不存在，就添加进去
		
		// 第一种遍历方式
		for(String key :chm.keySet()){
			System.out.println(chm.get(key));
		}
		
		for(Map.Entry<String, Object> map : chm.entrySet()){
			System.out.println("key值为："+map.getKey()+",value值为："+map.getValue());
		}
	}
}
