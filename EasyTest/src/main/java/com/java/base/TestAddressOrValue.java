package com.java.base;
/**
 * 测试是值传递还是址传递
 * @author Administrator
 *
 */
public class TestAddressOrValue {

	public void setName(Vo vo){
		Vo vo1 = new Vo();
		vo.setName("bbbbbbbb");
		vo1.setName("new");
		vo = vo1;//对引用的复制，所以name != new
	}
	public static void main(String[] args) {
		TestAddressOrValue t = new TestAddressOrValue();
		Vo vo = new Vo();
		vo.setName("aaa");
		t.setName(vo);
		System.out.println(vo.getName());
	}
}
