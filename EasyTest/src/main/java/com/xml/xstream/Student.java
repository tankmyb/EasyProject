package com.xml.xstream;
public class Student {
    private int id;
    private String name;
    private String email;
    private String address;
    //getter、setter
    public String toString() {
        return this.name + "#" + this.id + "#" + this.address  + "#" + this.email;
    }
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
}