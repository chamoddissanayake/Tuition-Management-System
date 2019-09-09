package com.test.tuitionmanagementsystem;

public class Fe {
    private String fid;
    private String sid;
    private String sName;
    private String subject;
    private String feedback;

    public Fe() {
    }

    public Fe(String fid, String sid, String sName, String subject, String feedback) {
        this.fid = fid;
        this.sid = sid;
        this.sName = sName;
        this.subject = subject;
        this.feedback = feedback;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
