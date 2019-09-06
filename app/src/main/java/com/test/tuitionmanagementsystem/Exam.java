package com.test.tuitionmanagementsystem;

public class Exam {
    String ExamID, DateTime,Venue, Description;

    public Exam(String examID, String dateTime, String venue, String description) {
        ExamID = examID;
        DateTime = dateTime;
        Venue = venue;
        Description = description;
    }

    public Exam() {
    }

    public String getExamID() {
        return ExamID;
    }

    public void setExamID(String examID) {
        ExamID = examID;
    }

    public String getDateTime() {
        return DateTime;
    }

    public void setDateTime(String dateTime) {
        DateTime = dateTime;
    }

    public String getVenue() {
        return Venue;
    }

    public void setVenue(String venue) {
        Venue = venue;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
