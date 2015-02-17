package com.java.clone.light;
class Employee implements Cloneable{
    private String username;
    private Employer employer;
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public Employer getEmployer() {
        return employer;
    }
    public void setEmployer(Employer employer) {
        this.employer = employer;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        //克隆Employee对象并手动的进一步克隆Employee对象中包含的Employer对象
        Employee employee = (Employee)super.clone();
        employee.setEmployer((Employer) employee.getEmployer().clone());
        return employee;
    }
}