package com.test.tuitionmanagementsystem;

public class StaffCredentials {
    String tID, securedPassword,salt;

    public StaffCredentials(String tID, String securedPassword, String salt) {
        this.tID = tID;
        this.securedPassword = securedPassword;
        this.salt = salt;
    }

    public StaffCredentials() {
    }

    public String gettID() {
        return tID;
    }

    public void settID(String tID) {
        this.tID = tID;
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
