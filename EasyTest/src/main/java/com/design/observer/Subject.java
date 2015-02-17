package com.design.observer;

public interface Subject {

	public void add(Observer observer);
	public void notifyObserver();
	public void remove(Observer observer);
}
