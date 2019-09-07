package com.test.tuitionmanagementsystem;

public class StudentDetails_tb {

    private String StudentName;
    private String AdmissionNo;
    private String address1;
    private  String tel;

    public StudentDetails_tb() {
    }

    public String getStudentName() {
        return StudentName;
    }

    public String getAdmissionNo() {
        return AdmissionNo;
    }

    public String getAddress(String trim) {
        return address1;
    }

    public String getContact(String trim) {
        return tel;
    }

    public void setStudentName(String studentName) {
        StudentName = studentName;
    }

    public void setAdmissionNo(String admissionNo) {
        AdmissionNo = admissionNo;
    }

    public void setAddress(String address) {
        address1 = address;
    }

    public void setContact(String contact) {
        tel = contact;
    }
}
