package com.java.clone.light;
class Employer implements Cloneable{
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}