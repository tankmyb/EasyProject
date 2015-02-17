package com.java.hashcode;

public class Test {

	/**
	 * 测试如果改变对象里某个属性，hashcode会不会变化，
	 * 测试结果：如果没有重写equals()和hashcode()方法的话，是不会变化的。
	 */
	private static void test1(){
		Vo vo = new Vo();
		vo.setId(1);
		vo.setName("name1");
		System.out.println("first hashcode:"+vo.hashCode());
		
		vo.setName("name2");
		System.out.println("second hashcode:"+vo.hashCode());
	}
	private static void test2(){
		Vo vo = new Vo();
		vo.setId(1);
		vo.setName("name1");
		System.out.println("first hashcode:"+vo.hashCode());
		
		Vo vo1 = new Vo();
		vo1.setId(1);
		vo1.setName("name1");
		
		System.out.println("equals:"+vo.equals(vo1));
		System.out.println("second hashcode:"+vo1.hashCode());
	}
	public static void main(String[] args) {
		test2();
	}
}
