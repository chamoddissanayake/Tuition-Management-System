package com.test.tuitionmanagementsystem;

public class Subject {
    private String sName;

    public Subject(String sName) {
        this.sName = sName;
    }

    public Subject() {
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }
}
