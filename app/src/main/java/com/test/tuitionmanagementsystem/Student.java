package com.test.tuitionmanagementsystem;

public class Student {
  private  String sName;
   private  String sid;
   private  String Address;
   private String tel;
   private String nic;

    public Student() {
    }

    public String getsName() {
        return sName;
    }

    public String getSid() {
        return sid;
    }

    public String getAddress() {
        return Address;
    }

    public String getTel() {
        return tel;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }
}
