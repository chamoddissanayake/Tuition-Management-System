package com.test.tuitionmanagementsystem;

public class StudentFollowSubject {
    String sID,  subName;

    public StudentFollowSubject(String sID, String subName) {
        this.sID = sID;
        this.subName = subName;
    }

    public StudentFollowSubject() {
    }

    public String getsID() {
        return sID;
    }

    public void setsID(String sID) {
        this.sID = sID;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }
}
