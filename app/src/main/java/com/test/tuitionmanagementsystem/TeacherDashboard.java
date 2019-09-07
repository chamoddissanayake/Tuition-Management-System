package com.test.tuitionmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class TeacherDashboard extends AppCompatActivity {

    TextView helloMsg;
    ImageButton ResultsMgtBtn,AttendanceMgtBtn,FeedbackNotificationMgtBtn, StudyMaterialsManagementBtn;
    Button teacherRegisterStudentBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //block screen rotation
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_dashboard);

        Intent intent = getIntent();
        final String tID = intent.getStringExtra("TeacherID");
        final String tName = intent.getStringExtra("tName");

        helloMsg = (TextView)findViewById(R.id.HiTeacherMessage);
        helloMsg.setText("Hi "+tName+",");

        ResultsMgtBtn = (ImageButton) findViewById(R.id.teacherResultsMgtBtn);
        AttendanceMgtBtn =(ImageButton) findViewById(R.id.teacherAttendanceMgtBtn);
        FeedbackNotificationMgtBtn =(ImageButton) findViewById(R.id.teacherFeedbackNotificationMgtBtn);
        StudyMaterialsManagementBtn = (ImageButton) findViewById(R.id.teacherStudyMaterialsManagementBtn);

        teacherRegisterStudentBtn = (Button) findViewById(R.id.teacherRegisterStudent);

        ResultsMgtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(),"Result Management Clicked",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(),TeacherResultManagementDashboard.class);
                i.putExtra("tID",tID);
                i.putExtra("tName",tName);
                startActivity(i);
            }
        });

        AttendanceMgtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String t = "teacher";
                Intent i = new Intent(getApplicationContext(),StudentRegistration.class);
                i.putExtra("ID",tID);
                i.putExtra("Name",tName);
                i.putExtra("Type",t);
                startActivity(i);
            }
        });


        FeedbackNotificationMgtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),ViewFeedback.class);
                startActivity(i);
            }
        });

        StudyMaterialsManagementBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),Homework.class);
                startActivity(i);
            }
        });

        teacherRegisterStudentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String t = "teacher";
                Intent i = new Intent(getApplicationContext(),TeacherRegistration.class);
                i.putExtra("ID",tID);
                i.putExtra("Name",tName);
                i.putExtra("Type",t);
                startActivity(i);

            }

        });


    }


}
