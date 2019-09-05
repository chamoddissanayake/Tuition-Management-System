package com.test.tuitionmanagementsystem;

public class StudentCredentials {
    String sID, securedPassword,salt;

    public StudentCredentials(String sID, String securedPassword, String salt) {
        this.sID = sID;
        this.securedPassword = securedPassword;
        this.salt = salt;
    }

    public StudentCredentials() {
    }

    public String getsID() {
        return sID;
    }

    public void setsID(String sID) {
        this.sID = sID;
    }

    public String getSecuredPassword() {
        return securedPassword;
    }

    public void setSecuredPassword(String securedPassword) {
        this.securedPassword = securedPassword;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
