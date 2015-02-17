package com.java.base;

class Triangle extends Shape {
	public int getSides() {
		return 3;
	}
}

class Rectangle extends Shape {
	public int getSides(int i) {
		return i;
	}
}

public class Shape {
	public boolean isSharp() {
		return true;
	}

	public int getSides() {
		return 0;
	}

	public int getSides(Triangle tri) {
		return 3;
	}

	public int getSides(Rectangle rec) {
		return 4;
	}

	public static void main(String[] args) {
		Triangle tri = new Triangle();
		System.out.println("Triangle is a type of sharp? " + tri.isSharp());
		Shape shape = new Triangle();
		System.out.println("My shape has " + shape.getSides() + " sides.");
	}
}