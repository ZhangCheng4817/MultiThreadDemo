package com.zc.thread.VectorDemo;

import java.util.Vector;

// java中的vector 向量类可扩充容量的动态数组 简单来说就是一个可变容量的数组
// java.util.vector提供了向量类(vector)以实现类似动态数组的功能
public class VectorDemo {
	
	public static void main(String[] args) {
		
		Vector vector = new Vector();
		
		// 1.添加对象
		vector.addElement(new Object());
		// 添加数值对象
		Integer i = new Integer(1);
		vector.addElement(i);
		System.out.println(vector);
		
		// 修改指定下标索引位置的元素
		vector.setElementAt(new Integer(100), 1);// 将下标为1的元素修改为100
		System.out.println(vector);
		
		// 在指定位置插入元素
		vector.insertElementAt(new String("123"), 2);
		vector.insertElementAt(new String("456"), 3);
		vector.insertElementAt(new String("123"), 4);
		System.out.println(vector);
		
		// 删除指定的元素，从头开始查找与需要删除元素相等的对象，如果找到，就移除，如果找到多个，就移除第一个匹配的元素
		vector.removeElement(new String("123"));
		System.out.println(vector);
		
		// 移除所有的元素
		vector.removeAllElements();
		System.out.println(vector.size());// 获取vector动态数组的长度
		
		
		
	}
}
