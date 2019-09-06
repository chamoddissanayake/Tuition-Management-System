package com.test.tuitionmanagementsystem;

public class Student {
    String sid, sName, tel, email, photoLink, Nic;
    int yor;
    public Student(String sid, String sName, String tel, String email, int yor, String photoLink, String nic) {
        this.sid = sid;
        this.sName = sName;
        this.tel = tel;
        this.email = email;
        this.yor = yor;
        this.photoLink = photoLink;
        Nic = nic;
    }

    public Student() {
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

    public int getYor() {
        return yor;
    }

    public void setYor(int yor) {
        this.yor = yor;
    }

    public String getPhotoLink() {
        return photoLink;
    }

    public void setPhotoLink(String photoLink) {
        this.photoLink = photoLink;
    }

    public String getNic() {
        return Nic;
    }

    public void setNic(String nic) {
        Nic = nic;
    }


}
