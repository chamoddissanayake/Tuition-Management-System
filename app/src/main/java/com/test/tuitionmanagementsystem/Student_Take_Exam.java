package com.test.tuitionmanagementsystem;

public class Student_Take_Exam {
    String sID, subName, examID, documentLink;
    int mark;

    public Student_Take_Exam(String sID, String subName, String examID, int mark, String documentLink) {
        this.sID = sID;
        this.subName = subName;
        this.examID = examID;
        this.mark = mark;
        this.documentLink = documentLink;
    }

    public Student_Take_Exam() {
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

    public String getExamID() {
        return examID;
    }

    public void setExamID(String examID) {
        this.examID = examID;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public String getDocumentLink() {
        return documentLink;
    }

    public void setDocumentLink(String documentLink) {
        this.documentLink = documentLink;
    }
}
