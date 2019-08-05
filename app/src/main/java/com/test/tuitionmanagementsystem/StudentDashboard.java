package com.test.tuitionmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
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
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //block screen rotation
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);

        ResultsMgtBtn = (ImageButton) findViewById(R.id.studentResultsMgtBtn);
        AttendanceMgtBtn =(ImageButton) findViewById(R.id.studentAttendanceMgtBtn);
        FeedbackNotificationMgtBtn =(ImageButton) findViewById(R.id.studentFeedbackNotificationMgtBtn);
        StudyMaterialsManagementBtn = (ImageButton) findViewById(R.id.studentStudyMaterialsManagementBtn);

        Intent intent = getIntent();
        final String sID = intent.getStringExtra("StudentID");
        final String sName = intent.getStringExtra("sName");

        helloMsg = (TextView)findViewById(R.id.HiStudentMessage);
        helloMsg.setText("Hi "+sName+",");

        ResultsMgtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getApplicationContext(),"Result Management Clicked",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(),StudentResultManagementDashboard.class);
                i.putExtra("sID",sID);
                i.putExtra("sName",sName);
                startActivity(i);
            }
        });

        AttendanceMgtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String t = "student";
                Intent i = new Intent(getApplicationContext(),EditRegistration_Details.class);
                i.putExtra("ID",sID);
                i.putExtra("Name",sName);
                i.putExtra("Type",t);
                startActivity(i);
            }
        });

        FeedbackNotificationMgtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),FeedBack.class);
                i.putExtra("StudentID",sID);
                i.putExtra("sName",sName);
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
