package com.test.tuitionmanagementsystem;

public class Teacher {
    String tid, tName, Nic, qualification, tel, email, specialized_subject,photoLink;

    public Teacher(String tid, String tName, String nic, String qualification, String tel, String email, String specialized_subject, String photoLink) {
        this.tid = tid;
        this.tName = tName;
        Nic = nic;
        this.qualification = qualification;
        this.tel = tel;
        this.email = email;
        this.specialized_subject = specialized_subject;
        this.photoLink = photoLink;
    }

    public Teacher() {
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String gettName() {
        return tName;
    }

    public void settName(String tName) {
        this.tName = tName;
    }

    public String getNic() {
        return Nic;
    }

    public void setNic(String nic) {
        Nic = nic;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSpecialized_subject() {
        return specialized_subject;
    }

    public void setSpecialized_subject(String specialized_subject) {
        this.specialized_subject = specialized_subject;
    }

    public String getPhotoLink() {
        return photoLink;
    }

    public void setPhotoLink(String photoLink) {
        this.photoLink = photoLink;
    }
}
