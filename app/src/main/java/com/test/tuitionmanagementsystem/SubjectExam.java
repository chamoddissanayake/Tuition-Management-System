package com.test.tuitionmanagementsystem;

public class SubjectExam {
    String examID, subject;

    public SubjectExam(String examID, String subject) {
        this.examID = examID;
        this.subject = subject;
    }

    public SubjectExam() {
    }

    public String getExamID() {
        return examID;
    }

    public void setExamID(String examID) {
        this.examID = examID;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
