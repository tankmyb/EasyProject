package com.java.general;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
/**

 *泛型编程关键掌握两点:

 *1.在方法参数中使用通配符 

 *2.在方法的参数类型和返回类型中使用通用类型

 */

/**关键技术

 *1.通配符问号(?)表示任意类型.如"List<?>"表示可以存放任意对象类型的List

 *2.通配符可以接extends和super,表示有限制的通配符.如"List<? extends Parent>"

 *  声明的List能且仅能存放Parent及其子类的对象,而"List<? super Child>"

 *  声明的List仅能存放Child及其父类的对象

 *3.通用类型是指不指定参数或者返回值的类型,常用一个大写的字母代表类型,

 *  它能代表任何类型,需要在方法声明的返回值前用<>声明通用类型.如"public <T> String

 *  getName(T data)"的方法声明中,String前用<T>表示T是通用类型,它的data参数的类型是T,

 *  表示能接收任意类型的参数,方法的返回值是String类型

 */


public class GeneralProgram {
	/** 使用问号"?*通配符,"?"代表任何类型,所以它的参数可以是任何类型的Collection */

	public static void printCollection(Collection<?> collect) {

		if (collect == null) {

			return;

		}

		for (Object obj : collect) {

			System.out.println(obj + "  ");

		}

		System.out.println();

	}

	/** 使用有限制的通配符"? extends",可以接受任何Parent及其子类的Collection */

	public static void printNames(Collection<? extends Parent> collect) {

		if (collect == null) {

			return;

		}

		for (Object obj : collect) {

			System.out.println(obj + "  ");

		}

		System.out.println();

	}

	/** 将一个任意类型的数组添加到列表里 */

	public static <T> List<T> arrayToList(T[] datas) {

		if (datas == null) {

			return null;

		}

		List<T> list_T = new ArrayList<T>();

		for (T x : datas) {

			list_T.add(x);

		}

		return list_T;

	}

	/** 在一组parent对象中查找名字为name的Parent对象 */

	public static <T extends Parent> T findParent(T[] parents, String name) {

		if (parents == null) {

			return null;

		}

		T parent = null;

		// 依次遍历Parent对象数组

		for (T x : parents) {

			// 如果Parent对象的name与参数name匹配,则退出遍历

			if (x.name.equals(name)) {

				parent = x;

				break;

			}

		}

		// 返回

		return parent;

	}

	public static void main(String[] args) {

		/*** 指定具体的类型 ***/

		// 声明一个用于装字符串的列表,该列表只能装字符串的对象

		List<String> list_S = new ArrayList<String>();

		list_S.add("first");

		list_S.add("second");

		list_S.add("third");

		// 不能装整数对象

		Integer iObj = 10;

		// list_S.add(iObj);//error!!

		// 从列表中取值时,不用作强制类型转换

		String firstS = list_S.get(0);

		String thirdS = list_S.get(2);

		System.out.println("firstS: " + firstS + "; thirdS: " + thirdS);

		/*** 泛型和继承 ***/

		// String继承Object

		String s = "sss";

		Object o = s;

		// 但List<String>不继承List<Object>

		// List<Object> list_O = list_s;//error!!!!!!!!!

		/*** 通配符 ***/

		// 调用具有"?"通配符的方法

		List<Integer> list_I = new ArrayList<Integer>();

		list_I.add(5555);

		list_I.add(6666);

		list_I.add(7777);

		// 该方法可以打印整型列表,也可以打印字符串列表

		printCollection(list_I);

		printCollection(list_S);

		/*** 调用具有"?"通配符的方法 ***/

		// 只接受父类以及子类类型的列表

		List<Parent> list_Parent = new ArrayList<Parent>();

		list_Parent.add(new Parent("parentOne"));

		list_Parent.add(new Parent("parentTwo"));

		list_Parent.add(new Parent("parentThree"));

		List<Child> list_Child = new ArrayList<Child>();

		list_Child.add(new Child("childOne", 20));

		list_Child.add(new Child("childTwo", 22));

		list_Child.add(new Child("childThree", 21));

		printNames(list_Parent);

		printNames(list_Child);

		// 不能接受其它类型的参数

		// printNames(list_S);//error!

		/*** 泛型方法 ***/

		// arrayToList方法将任意类型的对象数组变成相应的列表

		Integer[] iObjs = { 55, 66, 77, 88, 99 };

		List<Integer> result_I = arrayToList(iObjs);// 转换整型数组

		printCollection(result_I);

		String[] ss = { "temp", "temptemp", "hehe", "he", "hehehe" };

		// 转换字符串数组

		List<String> result_S = arrayToList(ss);

		printCollection(result_S);

		// findParent方法在一组Parent对象中根据name查找Parent

		Parent[] parents = { new Parent("abc"), new Parent("bcd"),
				new Parent("def") };

		Parent parent = findParent(parents, "bcd");

		System.out.print("找到的bcd: " + parent);

		Child[] children = { new Child("abc", 22), new Child("bcd", 23),
				new Child("def", 22) };

		Child child = findParent(children, "bcd");

		System.out.print("找到的bcd: " + child);

		// 但是不能在字符串数组中进行查找

		// String sss = findparent(ss,"temp");//error!

	}

}

class Parent {

	public String name;

	public Parent(String name) {

		this.name = name;

	}

	public String toString() {

		return "name = " + this.name;

	}

}

class Child extends Parent {

	public int age;

	public Child(String name, int age) {

		super(name);

		this.age = age;

	}

	public String toString() {

		return super.toString() + "; age = " + age;

	}

}
