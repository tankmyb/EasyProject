package com.java.clone.light;

public class Snippet {
	public static void main(String[] args) throws CloneNotSupportedException {
	    Employer employer = new Employer();
	    employer.setUsername("arthinking");
	
	    Employee employee = new Employee();
	    employee.setUsername("Jason");
	    employee.setEmployer(employer);
	
	    //employee2由employee深复制得到
	    Employee employee2 = (Employee) employee.clone();
	    System.out.println(employee.getUsername()==employee2.getUsername());
	    //这样两个employee各自保存了两个employer
	    employee2.getEmployer().setUsername("Jason");
	    System.out.println(employee.getEmployer().getUsername());
	    System.out.println(employee2.getEmployer().getUsername());
	}
}

