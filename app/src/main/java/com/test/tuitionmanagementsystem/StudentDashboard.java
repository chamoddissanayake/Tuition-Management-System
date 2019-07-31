package com.test.tuitionmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class StudentDashboard extends AppCompatActivity {
    TextView helloMsg;
    ImageButton ResultsMgtBtn,AttendanceMgtBtn,FeedbackNotificationMgtBtn,StudyMaterialsManagementBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);

        ResultsMgtBtn = (ImageButton) findViewById(R.id.studentResultsMgtBtn);
        AttendanceMgtBtn =(ImageButton) findViewById(R.id.studentAttendanceMgtBtn);
        FeedbackNotificationMgtBtn =(ImageButton) findViewById(R.id.studentFeedbackNotificationMgtBtn);
        StudyMaterialsManagementBtn = (ImageButton) findViewById(R.id.studentStudyMaterialsManagementBtn);

        Intent intent = getIntent();
        String sID = intent.getStringExtra("StudentID");
        String sName = intent.getStringExtra("sName");

        helloMsg = (TextView)findViewById(R.id.HiStudentMessage);
        helloMsg.setText("Hi "+sName+",");

        ResultsMgtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Result Management Clicked",Toast.LENGTH_SHORT).show();
            }
        });

        AttendanceMgtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Attendance Management Clicked",Toast.LENGTH_SHORT).show();
            }
        });

        FeedbackNotificationMgtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),FeedBack.class);
                startActivity(i);
            }
        });

        StudyMaterialsManagementBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Study Materials Management Clicked",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
