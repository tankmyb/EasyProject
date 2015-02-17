package com.java.hashcode;

import java.util.HashSet;

public class Test1 {
	public static void main(String[] args) {
		HashSet set = new HashSet();
		for (int i = 0; i <= 3; i++) {
			set.add(new Demo1(i, i));
		}
		System.out.println(set);
		set.add(new Demo1(1, 1));
		System.out.println(set);
		System.out.println(set.contains(new Demo1(0, 0)));
		System.out.println(set.add(new Demo1(1, 1)));
		System.out.println(set.add(new Demo1(4, 4)));
		System.out.println(set);
	}

	private static class Demo1 {
		private int value;

		private int id;

		public Demo1(int value, int id) {
			this.value = value;
			this.id = id;
		}

		public String toString() {
			return " value = " + value;
		}

		public boolean equals(Object o) {
			Demo1 a = (Demo1) o;
			return (a.value == value) ? true : false;
		}

		
	}
}
