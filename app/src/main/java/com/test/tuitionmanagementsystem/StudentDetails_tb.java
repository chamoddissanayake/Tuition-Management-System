package com.test.tuitionmanagementsystem;

public class StudentDetails_tb {

    private String StudentName;
    private String AdmissionNo;
    private String address;
    private  String tel;

    public StudentDetails_tb() {
    }

    public String getStudentName() {
        return StudentName;
    }

    public void setStudentName(String studentName) {
        StudentName = studentName;
    }

    public String getAdmissionNo() {
        return AdmissionNo;
    }

    public void setAdmissionNo(String admissionNo) {
        AdmissionNo = admissionNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
