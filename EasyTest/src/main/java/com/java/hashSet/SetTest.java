package com.java.hashSet;

import java.util.HashSet;
import java.util.Set;

/**
 * 1.如何保证无序不重复
 * 
 * Java中每一个对象都对应一个int型的哈希码，在向HashSet添加元素时，JVM会通过对象的HashCode()方法获取此对象的哈希码，
 * 然后对某一个整数
 * （hashSet的初始容量）求余，将这个元素放在Set中位于模数的位置。如果该位置已存在元素，即出现要把两个元素放在同一位置的时候，如s1和s2
 * ，会判断s1.equal(s2)，如果值为真，说明s1和s2相同，抛弃等放入的元素。如果为假，继续遍历set里面其它的元素，调用equals方法，
 * 确保没有相同的元素后
 * ，将s2放置在另一空闲位置。为了实现及达到性能保障，应尽量做到：1).一定要保证相同对象的哈希码相同2).尽量保证不同的对象返回不同的哈希码
 * ,以尽可能保证求余后的模数相等，而减少equals的调用。但是应注意：我们永远无法保证不同的对象的哈希码就一定不同(因为哈希码是int型的，
 * 当我们申请的对象个数比int型的数据还要多时，出现相同的情况是必然)
 * 
 * 2.初始容量和加载因子的概念
 * 
 * 无参的构造方法生成的HashSet对象，其底层HashMap实例的默认初始容量是16，加载因子是0.75，当向hashSet中加入对象时，
 * 随着对象个数的增多，会导致模数相同的可能性越来越大，也就是需要调用equal()方法的可能性越来越大，这就导致了很多不必要的开销，为了解决这一问题，
 * Sun公司提出了加载因子的概念
 * ，即当hashSet尚未填满时(只需达到默认初始容量*加载因子，即16*0.75)，就可以动态扩大容量，(增大为原容量的2倍，
 * 即32)，这样哈希码就不再是对16求模了，而是对32求模，通过多开辟空间有效地降低了时间复杂度。
 * 
 * 3.性能分析
 * 
 * HashSet实现Set接口，由哈希表(实际上是一个HashMap实例)支持。它不保证集合的迭代顺序，特别是它不保证该顺序恒久不变。
 * 此类允许使用null元素
 * 。此类为基本操作提供了稳定性能，这些基本操作包括add、remove、contains和size，假定哈希函数将这些元素正确地颁在桶中
 * 。对此集合进行迭代所需的时间与HashSet实例的大小
 * (元素的数量)和底层HashMap实例(桶的数量)的"容量"的和成比例。因此，如此迭代性能很重要，则不要将寝容量设置得太高(或将加载因子设置得太低).
 * 
 * 4.同步分析
 * 
 * 注意，此实现不是同步的。
 * 如果多个线程同时访问一个集合，而其中至少一个线程修改了该集合，那么它必须保持外部同步。这通常是通过对自然封装该集合的对象执行同步操作来完成的
 * 。如果不存在这样的对象，则应该使用 Collections.synchronizedSet 方法来“包装”集合。最好在创建时完成这一操作，以防止对
 * HashSet 实例进行意外的不同步访问：Set s = Collections.synchronizedSet(new HashSet(...));
 * 
 * 5.避免内存泄露
 * 
 * 在下面的实例中，如下操作 s1.age=21;
 * test.remove(s1);并没有真正删除s1，这是因为在Student的hashcode方法中利用age来获取哈希值
 * ，当s1在加入哈希表之后，再对age
 * (或name)进行修改，将会改变对象的原哈希值，哈希表会根据变动调整对象的位置，而remove方法仍在试图删除原位置的对象
 * (此时可能已经为空)，最终的结果是根本没有删除成功
 * 。这样做很可能会导致大量的无效对象(而程序员误认为此类对象已经游离，可以被GC回收)存在于set中，从而导致内存泄露。如在本例中，s1 = new
 * Student("Pato",16);之后，s1原来指向的对象，GC不会做回收操作。
 * 
 * 
 * @author Administrator
 * 
 */
public class SetTest {
	public static void main(String[] args) {

		// 无参的构造方法生成的HashSet对象，其底层HashMap实例的默认初始容量是16，加载因子是0.75

		Set<Student> test = new HashSet<Student>();

		Student s1 = new Student("Mary", 18);

		Student s2 = new Student("Charles", 20);

		Student s3 = new Student("Herry", 18);

		test.add(s1);

		test.add(s2);

		test.add(s3);

		System.out.println("Size of Initial: " + test.size());// 3

		System.out.println(test);// [Student: Charles , 20, Student: Mary , 18,
									// Student: Herry , 18]

		test.remove(s1);

		System.out.println("Size of Removed: " + test.size());// 2

		System.out.println(test);// [Student: Charles , 20, Student: Herry , 18]

		test.add(s1);

		System.out.println("Size of Added: " + test.size());// 3

		System.out.println(test);// [Student: Charles , 20, Student: Mary , 18,
									// Student: Herry , 18]

		s1.age = 21;

		test.remove(s1);

		System.out.println("Size of Update-Remove: " + test.size());// 3
																	// *************************remove失效

		System.out.println(test);// [Student: Charles , 20, Student: Mary , 21,
									// Student: Herry , 18]

		s1 = new Student("Pato", 16);

		test.add(s1);

		System.out.println("Size of Added: " + test.size());// 4

		System.out.println(test);// [Student: Charles , 20, Student: Mary , 21,
									// Student: Pato , 16, Student: Herry , 18]

	}

}

class Student {

	public String name;

	public int age;

	public Student() {
	}

	public Student(String name, int age) {

		this.name = name;

		this.age = age;

	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;

		if (obj == null)
			return false;

		if (!(obj instanceof Student))
			return false;

		Student s = (Student) obj;

		if (this.name.equalsIgnoreCase(s.name) && this.age == s.age)
			return true;

		else
			return false;

	}

	@Override
	public int hashCode() {

		return name.toLowerCase().hashCode() + age;

	}

	@Override
	public String toString() {

		return "Student:  " + name + " , " + age;

	}

}
