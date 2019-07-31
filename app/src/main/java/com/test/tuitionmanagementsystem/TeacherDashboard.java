package com.test.tuitionmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class TeacherDashboard extends AppCompatActivity {

    TextView helloMsg;
    ImageButton ResultsMgtBtn,AttendanceMgtBtn,FeedbackNotificationMgtBtn, StudyMaterialsManagementBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_dashboard);

        Intent intent = getIntent();
        String tID = intent.getStringExtra("TeacherID");
        String tName = intent.getStringExtra("tName");

        helloMsg = (TextView)findViewById(R.id.HiTeacherMessage);
        helloMsg.setText("Hi "+tName+",");

        ResultsMgtBtn = (ImageButton) findViewById(R.id.teacherResultsMgtBtn);
        AttendanceMgtBtn =(ImageButton) findViewById(R.id.teacherAttendanceMgtBtn);
        FeedbackNotificationMgtBtn =(ImageButton) findViewById(R.id.teacherFeedbackNotificationMgtBtn);
        StudyMaterialsManagementBtn = (ImageButton) findViewById(R.id.teacherStudyMaterialsManagementBtn);

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
                Toast.makeText(getApplicationContext(),"Feedback Management Clicked",Toast.LENGTH_SHORT).show();
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
