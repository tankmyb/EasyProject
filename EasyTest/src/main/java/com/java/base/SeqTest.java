package com.java.base;

class Sub1 extends Sub{
	public Sub1(){
		super();
		System.out.println("===========sub1==="+s);
	}
	protected void init(){
		System.out.println(s+"===sub1");
		super.init();
	}
}
abstract class Sub extends Parent{
	 protected String s=getStr();
	public Sub(){
		super();
		System.out.println("===========sub==="+s);
	}
	protected void init(){
		System.out.println(s+"===sub");
		super.init();
	}
	private String getStr(){
		System.out.println("=================2");
		return "aaaaa";
	}
	public String getS(){
		return s;
	}
	
}
 abstract class Parent implements Int{
	
		public Parent(){
			System.out.println("==============");
			init();
		}
		protected void init(){
			System.out.println("=============1init");
			//s=getStr();
		}
 }
 interface Int{
	 public String getS();
 }
 class SeqTest {

	
	public static void main(String[] args) {
		Int i = new Sub1();
		System.out.println(i.getS());
	}
}
